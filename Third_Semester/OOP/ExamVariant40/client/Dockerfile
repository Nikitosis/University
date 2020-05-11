# Use an official node runtime as a parent image
FROM node:12.2.0-alpine

COPY . /app

WORKDIR /app

RUN npm install

EXPOSE 3000

CMD ["npm","start"]