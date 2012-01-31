package score.storage

class NullStorage extends Storage(null)(null) with ReadOnlyStorage with WriteOnlyStorage {
  protected override def operationNotSupported = {sys.error("Operation is not supported for null-storage.")}
}

