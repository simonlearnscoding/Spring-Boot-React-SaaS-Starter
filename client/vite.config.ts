import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import tailwindcss from "@tailwindcss/vite";
import path from "path";

const isDocker = process.env.DOCKER === "true";

export default defineConfig({
  plugins: [react(), tailwindcss()],
  server: {
    watch: {
      usePolling: true,
    },
    host: true,
    port: 3000,
    fs: {
      allow: isDocker
        ? ["/app"] // Docker container path
        : [path.resolve(__dirname)], // Local dev path (host machine)
    },
  },
});
