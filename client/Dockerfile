
# client/Dockerfile
#FROM node:18-alpine
#WORKDIR /app
#COPY . .
#RUN yarn install
#EXPOSE 3000
#CMD ["yarn", "start"]

### Corrected frontend/Dockerfile for Vite-powered React Dev Server

FROM node:18
WORKDIR /app

COPY package.json yarn.lock ./

RUN yarn install --production=false

COPY . .

EXPOSE 3000
CMD ["yarn", "dev", "--host", "0.0.0.0"]