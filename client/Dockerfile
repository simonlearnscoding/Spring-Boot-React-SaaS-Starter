# Stage 1: Build the Vite app
FROM node:18-alpine as builder
WORKDIR /app

# Set Rollup to use Node.js (fixes Alpine musl issues)
ENV ROLLUP_ADAPTER=node

# Copy package files first (better caching)
COPY package.json yarn.lock ./

# Install deps (including devDependencies for build)
RUN yarn install --frozen-lockfile --production=false

# Copy source and build
COPY . .
RUN yarn build  # Runs `vite build` (outputs to /app/dist)

# Stage 2: Serve static files
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Copy built files from builder
COPY --from=builder /app/dist .

# Optional: Custom nginx config (e.g., for SPA routing)
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
