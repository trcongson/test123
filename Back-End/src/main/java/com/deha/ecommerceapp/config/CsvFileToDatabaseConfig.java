package com.deha.ecommerceapp.config;

import com.deha.ecommerceapp.dto.OrderProductDto;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@EnableBatchProcessing
@Configuration
public class CsvFileToDatabaseConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    private Resource output = new FileSystemResource("resources/outputData.csv");

    @Bean
    public JdbcCursorItemReader<OrderProductDto> reader(){
        JdbcCursorItemReader<OrderProductDto> reader = new JdbcCursorItemReader<OrderProductDto>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT id, name, price FROM Product");
        reader.setSql("SELECT id, dateCreated FROM orders");
        reader.setSql("SELECT quantity FROM OrdetProduct");
        reader.setRowMapper(new OrderProductDtoRowMapper());
        return reader;
    }

    public class OrderProductDtoRowMapper implements RowMapper<OrderProductDto> {

        @Override
        public OrderProductDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderProductDto orderProductDto = new OrderProductDto();
            orderProductDto.getProduct().setId(rs.getLong("product_id"));
            orderProductDto.getProduct().setName(rs.getString("name"));
            orderProductDto.getProduct().setPrice(rs.getDouble("price"));
            orderProductDto.getOrder().setId(rs.getLong("order_id"));
            orderProductDto.setQuantity(rs.getInt("quantity"));
            return orderProductDto;
        }
    }

    public OrderProductDtoItemProcessor processor() {
        return new OrderProductDtoItemProcessor();
    }

    @Bean
    public FlatFileItemWriter<OrderProductDto> writer() {
        FlatFileItemWriter<OrderProductDto> writer = new FlatFileItemWriter<OrderProductDto>();
        writer.setResource(output);
        writer.setLineAggregator(new DelimitedLineAggregator<OrderProductDto>(){{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<OrderProductDto>(){{
                setNames(new String[] {"produc_id","name","price","order_id","quantity"});
            }});
        }});
        return writer;
    }

//    @Bean
//    public JdbcBatchItemWriter<OrderProductDto> csvOrderProductWriter() {
//        JdbcBatchItemWriter<OrderProductDto> csvOrderProductWriter = new JdbcBatchItemWriter<OrderProductDto>();
//        csvOrderProductWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<OrderProductDto>());
//        csvOrderProductWriter.setSql("INSERT INTO Product (id, name, price) VALUES (:id, :name, :price)");
//        csvOrderProductWriter.setSql("INSERT INTO orders (id, dateCreated) VALUES (:id, :dateCreated)");
//        csvOrderProductWriter.setDataSource(dataSource);
//        return csvOrderProductWriter;
//    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("csvFileToDatabaseStep").<OrderProductDto, OrderProductDto> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job writeCSVFileJob() {
        return jobBuilderFactory
                .get("writeCSVFileJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

}
