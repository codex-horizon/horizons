const {defineConfig} = require('@vue/cli-service');
module.exports = defineConfig({
    transpileDependencies: true,
    publicPath: process.env.NODE_ENV ? process.env.VUE_APP_CONTEXT_PATH : process.env.VUE_APP_CONTEXT_PATH,
    outputDir: './dist',
    assetsDir: 'static',
    // configureWebpack: (config) => {
    //     if (process.env.NODE_ENV === 'production') {
    //         console.info(config);
    //     } else {
    //         console.info(config);
    //     }
    // },
    chainWebpack: (config) => {
        // if (process.env.NODE_ENV === 'production') {
        //     console.info(config);
        // } else {
        //     console.info(config);
        // }
        config.plugin('html').tap(args => {
            args[0].title = '✨✨Efficiency✨✨';
            return args;
        });
    }
});