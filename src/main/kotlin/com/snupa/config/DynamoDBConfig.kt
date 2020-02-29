package com.snupa.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.snupa.repository.SettingRepository
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DynamoDBConfig (
        @Autowired private val amazonDynamoDB : AmazonDynamoDB
) {

    @Value("\${amazon.dynamoDB.endpoint}")
    private val amazonDynamoDBEndpoint : String? = null
    @Value("\${amazon.dynamoDB.region}")
    private val amazonDynamoDBRegion : String? = null
    @Value("\${amazon.accessKey}")
    private val amazonAwsAccessKey : String? = null
    @Value("\${amazon.secretKey}")
    private val amazonAwsSecretKey : String? = null

    @Bean
    fun amazonDynamoDB() : AmazonDynamoDB {
        val credentialProvider = AWSStaticCredentialsProvider(BasicAWSCredentials(amazonAwsAccessKey, amazonAwsSecretKey))
        val endpointConfiguration = AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, amazonDynamoDBRegion)

        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(credentialProvider)
                .withEndpointConfiguration(endpointConfiguration).build()
    }

    fun getDynamoDBMapper() : DynamoDBMapper {
        return DynamoDBMapper(
                amazonDynamoDB,
                DynamoDBMapperConfig.DEFAULT
        )
    }

    fun getDynamoDBMapper(config: DynamoDBMapperConfig) : DynamoDBMapper{
        return DynamoDBMapper(
                amazonDynamoDB,
                config
        )
    }
}
