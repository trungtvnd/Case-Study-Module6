// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  apiAuth : 'http://localhost:8080/api/auth',
  firebaseConfig : {
    apiKey: "AIzaSyCiOEKBo_4ISmCL0tuvk5lMy495B0G6-Oc",
    authDomain: "testuploadfile-eacce.firebaseapp.com",
    databaseURL : 'https://testuploadfile-eacce-default-rtdb.firebaseio.com',
    projectId: "testuploadfile-eacce",
    storageBucket: "testuploadfile-eacce.appspot.com",
    messagingSenderId: "692403935652",
    appId: "1:692403935652:web:da13a77dc71e7fa70c8931",
    measurementId: "G-424L9ZS0TJ"
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
