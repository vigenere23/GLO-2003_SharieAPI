const { exec } = require('child_process')
const opn = require("opn")

console.log("Generating documentation...")
exec("apidoc -i ./src/ -o ./apidoc/ -f .java", (error, stdout, stderr) => {
  if (error) {
    console.log(error)
    console.log(stderr)
  }
  else {
    console.log(stdout)
    console.log("Opening default browser...")
    opn("./apidoc/index.html")
  }
})
