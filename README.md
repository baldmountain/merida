![merida](doc/merida.jpg)

# merida

A basic clojure pedestal app that uses components to separate concerns and reload.repl to handle code reloading.

## Getting Started

1. Start the application: `lein run`
2. Go to [localhost:4000](http://localhost:4000/) to see: `Hello World!`
3. Read your app's source code at src/merida/system.clj. Explore the docs of functions
   that define routes and responses.
4. Run your app's tests with `lein test`. Read the tests at test/merida/system_test.clj.
5. Learn more! See the [Links section below](#links).


## Configuration

To configure logging see config/logback.xml. By default, the app logs to stdout and logs/.
To learn more about configuring Logback, read its [documentation](http://logback.qos.ch/documentation.html).


## Developing your service

1. Start a new REPL: `lein repl`
2. Start your service in dev-mode: `(go)`
3. Connect your editor to the running REPL session.
   Re-evaluated code will be seen immediately in the service.
4. You can restart the app and reload code changes using `(reset)`

### [Docker](https://www.docker.com/) container support

1. Build an uberjar of your service: `lein uberjar`
2. Build a Docker image: `sudo docker build -t merida .`
3. Run your Docker image: `docker run -p 4000:4000 merida`

### [OSv](http://osv.io/) unikernel support with [Capstan](http://osv.io/capstan/)

1. Build and run your image: `capstan run -f "4000:4000"`

Once the image it built, it's cached.  To delete the image and build a new one:

1. `capstan rmi merida; capstan build`


## Links
* [Other examples](https://github.com/pedestal/samples)

