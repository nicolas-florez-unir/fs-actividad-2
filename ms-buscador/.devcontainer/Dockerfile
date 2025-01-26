FROM mcr.microsoft.com/devcontainers/java:21-bullseye

# Definimos el directorio de trabajo dentro del contenedor
WORKDIR /workspace

# Instalamos cualquier otra herramienta que necesitemos
RUN apt-get update && apt-get install -y \
    maven \
    curl 

EXPOSE 8080