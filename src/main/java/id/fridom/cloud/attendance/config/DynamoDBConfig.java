package id.fridom.cloud.attendance.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import id.fridom.cloud.attendance.repositories.AttendanceRepository;
import id.fridom.cloud.common.constants.GenericConstants;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Class for changing the initial configuration of DynamoDB client.
 */
@Configuration
@EnableDynamoDBRepositories(basePackageClasses = AttendanceRepository.class)
public class DynamoDBConfig {

    private final String env = System.getenv(GenericConstants.ENV_KEY);

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard().build();
    }

    /**
     * Setting the prefix of DynamoDB tablenames. The format is fridom-environment-entity:
     * @return The object with the prefix set.
     */
    @Bean
    public DynamoDBMapperConfig.TableNameOverride tableNameOverride() {
        return DynamoDBMapperConfig.TableNameOverride.withTableNamePrefix(
                GenericConstants.DYNAMODB_TABLENAME_PREFIX + env + "-");
    }

    @Bean
    @Primary
    public DynamoDBMapperConfig dynamoDBMapperConfig() {
        return DynamoDBMapperConfig.builder().withTableNameOverride(tableNameOverride()).build();
    }

    @Bean
    @Primary
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(amazonDynamoDB(), dynamoDBMapperConfig());
    }
}
