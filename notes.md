Maven
* import Morphia
* import MongoDB

class MorphiaListingsDAO {

  private final Datastore datastore;

  constructor () {
    Morphia morphia = new Morphia()
    morphia.mapPachakge("gloxx.xxxx.models")
    String host = new ServerAdress("secret url with password from mLab")
    MongoClient mongoCLient = new MongoClient(host)
    datastore = morphia.createDatastore(mongoClient, "a nae for the datastore")
  }

  getAll() {
    query = datastore.createQuery(Listing.class)
    return query.asList()
  }

  save(Listing listing) {
    datastore.save(listing)
  }

}


Optional.ofNullable(System.getenv("SHARIE_DATABASE_URL")).orElse("defaultValue");

export le serveur en mode non static avec class et constructeur
ajouter Main.java (SharieApp.java) pour démarrer le serveur

new dao
new controller from dao
inject to server