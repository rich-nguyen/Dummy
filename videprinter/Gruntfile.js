/* global module: false, process: false */
module.exports = function (grunt) {
    var isDev = grunt.option('dev') || process.env.GRUNT_ISDEV === '1'

    if (isDev) {
        grunt.log.subhead('Running Grunt in DEV mode');
    }

    // Project configuration.
    grunt.initConfig({

        requirejs: {
            compile: {
                options: {
                    baseUrl: 'app/source_assets/javascripts',
                    name: "app",
                    out: "public/javascripts/app.js",
                    paths: {
                        bonzo:        "components/bonzo/src/bonzo",
                        qwery:        "components/qwery/mobile/qwery-mobile"

                    },
                    wrap: {
                        startFile: "app/source_assets/javascripts/components/curl/dist/curl-with-js-and-domReady/curl.js"
                    },
                    optimize: (isDev) ? 'none' : 'uglify2',
                    useSourceUrl: (isDev) ? true : false,
                    preserveLicenseComments: false
                }
            }
        },

        // Clean stuff up
        clean: {
            js: ['public/javascripts']
        }
    });

    // Load the plugins
    grunt.loadNpmTasks('grunt-contrib-requirejs');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-env');

    grunt.registerTask('default', ['compile']);

    grunt.registerTask('compile', function(app) {
        grunt.task.run(['clean:js', 'requirejs:compile']);

    });
};
