@echo off
SETLOCAL Enabledelayedexpansion
chcp 65001 >nul

echo ====================================================
echo [POUSO] Inicializador de Banco Inteligente via .env (Windows)
echo ====================================================

SET "ENV_FILE=.env"

:: 1. Valida se o arquivo .env existe
if not exist "%ENV_FILE%" (
    echo [ERRO] Arquivo %ENV_FILE% não encontrado!
    echo Por favor, crie o arquivo .env na raiz do projeto antes de rodar.
    pause
    exit /b 1
)

echo  -^> Carregando configurações do seu arquivo .env...

:: 2. Lê o arquivo .env e extrai as variáveis
for /f "usebackq delims=" %%a in ("%ENV_FILE%") do (
    set "line=%%a"
    :: Ignora linhas de comentário
    if not "!line:~0,1!"=="#" (
        for /f "tokens=1,2 delims=" %%b in ("%%a") do (
            set "%%b"
        )
    )
)

:: 3. Extrai o nome do banco a partir da DB_URL (pega tudo após a última barra)
set "DB_NAME="
set "temp_url=%DB_URL%"
:loop
for /f "tokens=1* delims=/" %%a in ("%temp_url%") do (
    set "DB_NAME=%%a"
    set "temp_url=%%b"
    if defined temp_url goto loop
)

if "%DB_USER%"=="" or "%DB_PASSWORD%"=="" or "%DB_NAME%"="" (
    echo [ERRO] Não foi possível ler as credenciais dentro do seu .env
    pause
    exit /b 1
)

echo  -^> Banco Detectado: %DB_NAME%
echo  -^> Usuário Detectado: %DB_USER%
echo ----------------------------------------------------
echo [1/2] Executando comandos no PostgreSQL do Windows...
echo (Se pedir a senha do usuário 'postgres' do sistema, digite-a)
echo ----------------------------------------------------

:: 4. Executa os comandos usando o psql padrão do Windows como superusuário 'postgres'
psql -U postgres -d postgres -c "CREATE USER %DB_USER% WITH PASSWORD '%DB_PASSWORD%';" 2>nul
psql -U postgres -d postgres -c "ALTER USER %DB_USER% WITH PASSWORD '%DB_PASSWORD%';"
psql -U postgres -d postgres -c "CREATE DATABASE %DB_NAME% OWNER %DB_USER%;" 2>nul
psql -U postgres -d postgres -c "GRANT ALL PRIVILEGES ON DATABASE %DB_NAME% TO %DB_USER%;"

echo ----------------------------------------------------
echo [2/2] Validando existência real do link do banco...
echo ----------------------------------------------------

:: 5. Testa a conexão local usando as credenciais do .env
set PGPASSWORD=%DB_PASSWORD%
psql -U %DB_USER% -d %DB_NAME% -h localhost -c "SELECT 'Conexão efetuada com sucesso!' as Status;" 2>nul

if %ERRORLEVEL% EQU 0 (
    echo.
    echo SUCESSO! O banco está vivo e aceitando conexões locais no Windows!
    echo Agora você já pode subir a aplicação com o comando:
    echo mvn spring-boot:run
) else (
    echo.
    echo ATENÇÃO: O banco foi criado, mas a tentativa de login por senha falhou.
    echo Verifique se a senha no .env confere ou se o serviço do Postgres está rodando.
)

echo ====================================================
pause
