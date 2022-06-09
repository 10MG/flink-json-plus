package cn.tenmg.flink.formats.json.debezium;

import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;
import org.apache.flink.formats.json.JsonFormatOptions;

/**
 * Option utils for debezium-json-plus format.
 * 
 * @author June wjzhao@aliyun.com
 * @since 1.1.0
 */
public class DebeziumJsonPlusFormatOptions {

	public static final ConfigOption<Boolean> SCHEMA_INCLUDE = ConfigOptions.key("schema-include").booleanType()
			.defaultValue(false)
			.withDescription("When setting up a Debezium Kafka Connect, users can enable "
					+ "a Kafka configuration 'value.converter.schemas.enable' to include schema in the message. "
					+ "This option indicates the Debezium JSON data include the schema in the message or not. "
					+ "Default is false.");

	public static final ConfigOption<Boolean> CONVERT_DELETE_TO_UPDATE = ConfigOptions.key("convert-delete-to-update")
			.booleanType().defaultValue(true).withDescription(
					"This option indicating whether to convert a delete record to a update record (default: true). "
							+ "Default is true.");

	public static final ConfigOption<Boolean> IGNORE_PARSE_ERRORS = JsonFormatOptions.IGNORE_PARSE_ERRORS;

	public static final ConfigOption<String> TIMESTAMP_FORMAT = JsonFormatOptions.TIMESTAMP_FORMAT;

	public static final ConfigOption<String> JSON_MAP_NULL_KEY_MODE = JsonFormatOptions.MAP_NULL_KEY_MODE;

	public static final ConfigOption<String> JSON_MAP_NULL_KEY_LITERAL = JsonFormatOptions.MAP_NULL_KEY_LITERAL;
}
