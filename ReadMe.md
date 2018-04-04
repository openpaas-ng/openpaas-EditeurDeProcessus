# Install

## Prerequis

We assume that openpaas is already installed and is prerequis(npm and bower)

## Generate File

Here the process to install the module for openpaas

  - npm install
  - grunt browserify

## Integrate it

Now this is quiet simple. Either copy your new module inside **esn_repository_root/modules** or create a **symbolic link**. Then register your module in the **esn_repository_root/config/default.json** file under the **modules** key.

Be aware that using a **symbolic link** will require to run **npm install** in the original directory of you module.

```
  ...
  "modules": [
    "linagora.esn.account",
    "linagora.esn.appstore",
    "linagora.esn.calendar",
    "linagora.esn.contact",
    "linagora.esn.contact.import",
    "linagora.esn.contact.import.twitter",
    "linagora.esn.contact.twitter",
    "linagora.esn.core.webserver",
    "linagora.esn.core.wsserver",
    "linagora.esn.cron",
    "linagora.esn.davproxy",
    "linagora.esn.davserver",
    "linagora.esn.digest.daily",
    "linagora.esn.jobqueue",
    "linagora.esn.graceperiod",
    "linagora.esn.messaging.email",
    "linagora.esn.oauth.consumer",
    "linagora.esn.project",
    "linagora.esn.unifiedinbox",
    "esn.bpmn"
  ],
  "email": {
    "templatesDir": "./templates/email"
  }
  ...
```

Note that here we have registered the **esn.bpmn** module. The name of the module is defined in the root index.js file.

```javascript
var myAwesomeModule = new AwesomeModule('esn.bpmn', {
  dependencies: [
    ...
  ],
  ...
}
```
