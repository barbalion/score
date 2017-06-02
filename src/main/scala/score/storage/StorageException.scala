package score.storage

class StorageException(val storage: Storage, message: String, cause: Throwable) extends Exception(message, cause) {
  def this(storage: Storage, message: String) = this(storage, message, null)
}
