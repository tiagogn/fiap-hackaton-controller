#!/bin/bash

echo "⏳ Inicializando recursos no LocalStack..."

# Criar bucket S3
awslocal s3 mb s3://videos

# Criar fila SQS
awslocal sqs create-queue --queue-name video-queue

# Verificar email no SES
awslocal ses verify-email-identity --email-address no-reply@example.com

echo "✅ Recursos criados com sucesso!"