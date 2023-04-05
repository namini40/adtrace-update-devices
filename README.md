## solution
the solution is to update all the devices updated the app.

when you change adtrace's `app_token`, there are local variables stored in shared preferences and cached files in local storage.
according to these variables adtrace initialized before and there is no need to init again.

## force sdk to send install again
in order to do that you have to delete related local files and prefs (not all of them) **ONLY FIRST TIME RUN AFTER UPDATE**.
this way sdk forced to send this information again and app is updated. not useless to mention, this won't have any effects on
your data (accuracy and integrity).