# MAS-Storage-Cordova
MAS-Storage-Cordova is the framework with the storage API capabilities for CA Mobile API Gateway.
*********************************************************

## Features
The MAS-Storage-Cordova framework provides the following features:

* Local Storage
* Cloud Storage

*********************************************************

## Get Started
For documentation visit our visit our [developer site](http://mas.ca.com/docs/).
*********************************************************

## How You Can Contribute
Contributions are welcome and much appreciated. To learn more, see the [Contribution Guidelines](https://github.com/CAAPIM/Cordova-MAS-Storage/blob/develop/CONTRIBUTING.md).

## Generate Reference documents
The reference documents for the MAS modules are generated using the JSDoc markdown language. JSDoc reads the JS files with annotations in comments, and generates an HTML output. It considers the comments that start with /**.

To install JSDoc, follow the instructions on [JSDoc](http://usejsdoc.org/) website.

The Cordova reference documents are available at our [mas.ca.com]( http://mas.ca.com/docs/cordova/1.4.00/sdk/) site.

Example:
An annotated comment for validateOTP method is as follows:
```
 /**
  Validate the OTP.
  * @param {function} successHandler user defined success callback
  * @param {function} errorHandler user defined error callback
  * @param {string} otp user defined one-time password to verify
  */
```
**Param** – Specifies a parameter.
**{string}**  – Specifies the return type of the method.
**otp** – Specifies the variable name.
**user defined one-time password to verify** – Describes the method.

Compile the JSDoc reference document as follows:
```
jsdoc www/ -d jsdocs
```
where “www/” is the location of the js file, and “-d jsdocs” is the location where the generated reference documents are placed.

For more information about how to write and compile the JSDoc reference documents, see the [JSDoc](http://usejsdoc.org/) website.

## Communication

- *Have general questions or need help?*, use [Stack Overflow][StackOverflow]. (Tag 'massdk')
- *Find a bug?*, open an issue with the steps to reproduce it.
- *Request a feature or have an idea?*, open an issue.

## License
Copyright (c) 2016 CA. All rights reserved.

This software may be modified and distributed under the terms of the MIT license. See the [LICENSE](https://github.com/CAAPIM/Cordova-MAS-Storage/blob/develop/LICENSE) file for details.
