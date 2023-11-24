const {defineConfig} = require('@vue/cli-service');
module.exports = defineConfig({
    transpileDependencies: true,
    publicPath: process.env.NODE_ENV ? process.env.VUE_APP_CONTEXT_PATH : process.env.VUE_APP_CONTEXT_PATH,
    outputDir: './dist',
    assetsDir: 'static',
    configureWebpack: (config) => {
        if (process.env.NODE_ENV === 'production') {
            console.info(config);
        } else {
            console.info(config);
        }
    },
    chainWebpack: (config) => {
        config.plugin('html').tap(args => {
            args[0].title = 'Efficiency Specification';
            return args;
        });
        config.optimization
            .minimize(true)
            .minimizer('terser')
            .tap(args => {
                let {terserOptions} = args[0];
                terserOptions.compress.drop_console = true;
                terserOptions.compress.drop_debugger = true;
                return args;
            });
    },
    filenameHashing: false
});