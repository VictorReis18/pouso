#!/bin/bash

ENV_FILE=".env"

echo "[POUSO] Inicializador de Banco via .env"

if [ ! -f "$ENV_FILE" ]; then
    echo "[ERRO] Arquivo $ENV_FILE não encontrado!"
    exit 1
fi

DB_USER=$(grep "^DB_USER=" $ENV_FILE | cut -d'=' -f2 | tr -d '\r')
DB_PASSWORD=$(grep "^DB_PASSWORD=" $ENV_FILE | cut -d'=' -f2 | tr -d '\r')
DB_URL=$(grep "^DB_URL=" $ENV_FILE | cut -d'=' -f2 | tr -d '\r')
DB_NAME=$(echo "$DB_URL" | grep -o '[^/]*$')

echo " -> Banco Alvo: $DB_NAME"
echo " -> Usuário Alvo: $DB_USER"
echo "----------------------------------------------------"
echo "[1/2] Forçando criação de infraestrutura no Postgres..."

doas -u postgres psql -h localhost -d postgres -c "CREATE USER $DB_USER WITH PASSWORD '$DB_PASSWORD';" 2>/dev/null
doas -u postgres psql -h localhost -d postgres -c "ALTER USER $DB_USER WITH PASSWORD '$DB_PASSWORD';"
doas -u postgres psql -h localhost -d postgres -c "CREATE DATABASE $DB_NAME OWNER $DB_USER;" 2>/dev/null
doas -u postgres psql -h localhost -d postgres -c "GRANT ALL PRIVILEGES ON DATABASE $DB_NAME TO $DB_USER;"

echo "----------------------------------------------------"
echo "[2/2] Validando existência real do link do banco:"

PGPASSWORD="$DB_PASSWORD" psql -h localhost -U "$DB_USER" -d "$DB_NAME" -c "SELECT 'Conexão efetuada com sucesso!' as Status;" 2>/dev/null

echo "----------------------------------------------------"
if [ $? -eq 0 ]; then
    echo ""
    echo "SUCESSO! O banco está vivo e aceitando conexões locais!"
    echo "Para rodar:"
    echo "mvn validate flyway:migrate spring-boot:run"
else
    echo ""
    echo "ERRO: O banco foi criado, mas o Postgres bloqueou o acesso por senha local."
fi
