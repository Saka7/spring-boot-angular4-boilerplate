module.exports = {
  navigateFallback: '/index.html',
  stripPrefix: 'dist',
  root: 'dist/',
  staticFileGlobs: [
    'dist/index.html',
    'dist/*.js',
    'dist/*.css',
    'dist/*.woff2',
    'dist/assets/favicon.png',
    'dist/assets/images/*.png',
    'dist/assets/images/*.jpg',
    'dist/assets/images/*.jpeg',
    'dist/assets/images/*.svg',
    'dist/assets/images/**/*.png',
    'dist/assets/images/**/*.jpg',
    'dist/assets/*.css',
    'dist/assets/styles/*.css',
    'dist/assets/*.json'
  ]
};