import json
import logging

logger = logging.getLogger()
logger.setLevel(logging.INFO)

def lambda_handler(event, context):
    # Log the entire event for reference
    logger.info("Received event: " + json.dumps(event))

    # Process each record in the event
    for record in event['Records']:
        # Extract relevant information from the record
        event_name = record['eventName']
        bucket_name = record['s3']['bucket']['name']
        object_key = record['s3']['object']['key']

        # Log the extracted information
        logger.info(f"Event Name: {event_name}")
        logger.info(f"Bucket Name: {bucket_name}")
        logger.info(f"Object Key: {object_key}")

        # Add your custom processing logic here

    return {
        'statusCode': 200,
        'body': json.dumps('Lambda function executed successfully!')
    }
