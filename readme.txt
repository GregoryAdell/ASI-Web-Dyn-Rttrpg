

Atelier 3 sur les branches  :

'cardMicroService'
'userMicroService'
'transactionMicroService'
'proxy' (frontend au final)


Voici aussi un docker-compose pour les projets (doivent etre à la suite selon cette archi : 


card-microservice/
frontend/
transaction-microservice/
user-microservice/
docker-compose.yaml
nginx.conf


docker-compose.yaml

version: "3.9"

services:
  postgres-user:
    image: postgres:15
    container_name: pg_user
    environment:
      POSTGRES_DB: userdb
      POSTGRES_USER: useradmin
      POSTGRES_PASSWORD: userpass
      SPRING_PROFILES_ACTIVE: docker
    ports:
      - "5433:5432"
    volumes:
      - pgdata_user:/var/lib/postgresql/data

  postgres-card:
    image: postgres:15
    container_name: pg_card
    environment:
      POSTGRES_DB: carddb
      POSTGRES_USER: cardadmin
      POSTGRES_PASSWORD: cardpass
      SPRING_PROFILES_ACTIVE: docker
    ports:
      - "5434:5432"
    volumes:
      - pgdata_card:/var/lib/postgresql/data

  postgres-transaction:
    image: postgres:15
    container_name: pg_transaction
    environment:
      POSTGRES_DB: transactiondb
      POSTGRES_USER: transadmin
      POSTGRES_PASSWORD: transpass
      SPRING_PROFILES_ACTIVE: docker
    ports:
      - "5435:5432"
    volumes:
      - pgdata_transaction:/var/lib/postgresql/data

  user-microservice:
    build:
      context: ./user-microservice
    container_name: user-service
    depends_on:
      - postgres-user
    ports:
      - "8081:8081"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres-user:5432/userdb
      SPRING_DATASOURCE_USERNAME: useradmin
      SPRING_DATASOURCE_PASSWORD: userpass
      SPRING_PROFILES_ACTIVE: docker

  card-microservice:
    build:
      context: ./card-microservice
    container_name: card-service
    depends_on:
      - postgres-card
    ports:
      - "8082:8082"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres-card:5432/carddb
      SPRING_DATASOURCE_USERNAME: cardadmin
      SPRING_DATASOURCE_PASSWORD: cardpass
      SPRING_PROFILES_ACTIVE: docker

  transaction-microservice:
    build:
      context: ./transaction-microservice
    container_name: transaction-service
    depends_on:
      - postgres-transaction
    ports:
      - "8083:8083"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres-transaction:5432/transactiondb
      SPRING_DATASOURCE_USERNAME: transadmin
      SPRING_DATASOURCE_PASSWORD: transpass
      SPRING_PROFILES_ACTIVE: docker
  
  reverse-proxy:
    image: nginx:latest
    container_name: nginx-proxy
    ports:
      - "80:80"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf:ro
    depends_on:
      - user-microservice
      - card-microservice
      - transaction-microservice
      - frontend
  
  frontend:
    build:
      context: ./frontend
    container_name: frontend
    expose:
      - "8080"


volumes:
  pgdata_user:
  pgdata_card:
  pgdata_transaction:




nginx.conf

events {}

http {
  upstream user-service {
    server user-service:8081;
  }

  upstream card-service {
    server card-service:8082;
  }

  upstream transaction-service {
    server transaction-service:8083;
  }

  upstream frontend {
    server frontend:8080;
  }

  server {
    listen 80;

    # --- API ROUTES ---
    location /api/users/ {
      proxy_pass http://user-service;
    }

    location /api/cards/ {
      proxy_pass http://card-service;
    }

    location /api/transactions/ {
      proxy_pass http://transaction-service;
    }

    location ^~ /api/ {
      return 404;
    }

    # --- FRONTEND ---
    location / {
      proxy_pass http://frontend;
      proxy_http_version 1.1;
      proxy_set_header Host $host;
      proxy_set_header X-Real-IP $remote_addr;
      proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
      proxy_set_header X-Forwarded-Proto $scheme;

      # Pour les routes côté client (SPA)
      try_files $uri $uri/ @frontend;
    }

    location @frontend {
      proxy_pass http://frontend;
    }
  }
}


