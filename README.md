# Beastify Plugin

[![forthebadge](http://forthebadge.com/images/badges/built-with-love.svg)](http://forthebadge.com)
[![forthebadge](http://forthebadge.com/images/badges/uses-js.svg)](http://forthebadge.com)

An overly simplified Firefox plugin made with [Kotlin](https://kotlinlang.org).

## Getting Started

You will need to have the [IntelliJ IDEA](https://www.jetbrains.com/idea/) installed on your development environment.

You will also need [node](https://nodejs.org/en/) and [npm](https://www.npmjs.com/) (or [yarn](https://yarnpkg.com/en/)) installed on your development environment.

Also, if you do not have [Firefox](https://www.mozilla.org/en-US/firefox/new/) installed, go ahead and download it for this project as this plugin targets firefox.

## Pre-requisites and Installation

A good knowledge of manipulation of the [DOM](https://developer.mozilla.org/en-US/docs/Web/API/Document_Object_Model/Introduction) and the Kotlin language is required, but for this project it is not necessary.

Install the dependencies you need

```bash
$ npm install
```
> This will install the JavaScript dependencies in root directory. This will be a simple dependency, [web-ext](https://developer.mozilla.org/en-US/Add-ons/WebExtensions/Getting_started_with_web-ext)

## Running the plugin

Running the plugin can be done as follows:

```bash
$ ./gradlew runDceKotlinJs --continuous
```

This will run the task `runDceKotlinJS` which will remove Dead code from the Kotlin Standard library that is not used (or called) and will watch changes made in the the modules.


```bash
$ npm run start
# or if using yarn
$ yarn start
```

This will open up the Firefox browser. You can now navigate to Firefox, open a tab and you should see a button option which will give you options of animals to select from.

An important thing to note:

*manifest.json*

There is a manifest.json file in the root of the directory which is used by the `web-ext` tool to set up the compiles JavaScript code from Kotlin. It should look like this:

```json
{
  "manifest_version": 2,
  "name": "<NAME OF PLUGIN>",
  "version": "1.0",
  "description": "<DESCRIPTION OF PLUGIN>",
  "content_scripts": [
    {
      "matches": [
        "*://kotlinlang.org/*"
      ],
      "js": [
        "build/classes/main/min/kotlin.js",
        "build/classes/main/min/borderify.js"
      ]
    }
  ]
}
```

Note the `js` tag which is a list of the JavaScript files the plugin will use. After running the
`gradle` task and no changes are reflected, make sure that the correct `js` files are being used

The JS file that the compiler will compile from Kotlin to JS is `build/classes/main/min/borderify.js` and the Kotlin standard library is `build/classes/main/min/kotlin.js`.

## Attributions

Inspiration comes from [@Cypresssious](https://medium.com/@Cypressious/your-second-firefox-extension-in-kotlin-bafd91d87c41)