import vue from '@vitejs/plugin-vue'
import { visualizer } from 'rollup-plugin-visualizer';
import { viteMockServe } from 'vite-plugin-mock'
import { createSvgIconsPlugin } from 'vite-plugin-svg-icons'
import viteImagemin from 'vite-plugin-imagemin'
import { resolve } from 'path'

const pathResolve = (dir) => {
  return resolve(__dirname, ".", dir)
}

const alias = {
  '@': pathResolve("src")
}

// https://vitejs.dev/config/
export default ({ command,mode }) => {
  const prodMock = true;
  console.log("------------------启动参数start-------------------")
  console.log(command)
  console.log(mode)
  console.log("----------------启动参数end---------------------")
  return {
    base: './',
    resolve: {
      alias
    },
    server: {
      port: 3003,
      host: '0.0.0.0',
      open: true,
      proxy: { // 代理配置
        '/dev': 'https://www.fastmock.site/mock/48cab8545e64d93ff9ba66a87ad04f6b/'
      },
    },
    build: {
      rollupOptions: {
        output: {
          manualChunks: {
            vue: ['vue', 'vue-router'],
            i18n:['vue-i18n'],
            mockjs:['mockjs'],
            element:['element-plus']
          }
        }
      }
    },
    plugins: [
      vue(),
      viteMockServe({
        mockPath: 'mock',
        localEnabled: command === 'serve',
        prodEnabled: command !== 'serve' && prodMock,
        watchFiles: true,
        injectCode: `
          import { setupProdMockServer } from '../mockProdServer';
          setupProdMockServer();
        `,
        logger: true,
      }),
      visualizer(),
      createSvgIconsPlugin({
        // 配置路劲在你的src里的svg存放文件
        iconDirs: [resolve(process.cwd(), 'src/assets/icons')],
        symbolId: 'icon-[dir]-[name]',
      }),
      viteImagemin({
        gifsicle: {
          optimizationLevel: 7,
          interlaced: false,
        },
        optipng: {
          optimizationLevel: 7,
        },
        mozjpeg: {
          quality: 20,
        },
        pngquant: {
          quality: [0.8, 0.9],
          speed: 4,
        },
        svgo: {
          plugins: [
            {
              name: 'removeViewBox',
            },
            {
              name: 'removeEmptyAttrs',
              active: false,
            },
          ],
        },
      })
    ]
  };
}
