package score

class IllegalOperation(message: String) extends Exception("Illegal operation: " + message) {
}

object IllegalOperation {
  def apply(message: String) = throw new IllegalOperation(message)
}

class NotImplementedYet() extends Exception("Not implemented yet.") {
}

object NotImplementedYet {
  def apply() = throw new NotImplementedYet()
}