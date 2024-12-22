import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  build: {
    outDir: 'dist'
  },
  server: {
    proxy: {
      '/v1/user': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      }
    }
  }
})
