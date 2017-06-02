package score.meta.model

import score.storage._
import score.{Context, SystemSession}

class ModelLoadingContext(aStorage: Storage) extends Context(null, SystemSession, aStorage) {

}

