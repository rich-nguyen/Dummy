package model

case class Spaceship(
  name: String,
  distanceTravelled: Float,
  fuelRemaining: Float,
  crew: List[CrewMember])
{
  def addMember(member: CrewMember) : Spaceship =
  {
    Spaceship(name, distanceTravelled, fuelRemaining, member :: crew)
  }

  def addFuel(fuel: Float) : Spaceship =
  {
    Spaceship(name, distanceTravelled, fuelRemaining + fuel, crew)
  }
}

case class CrewMember(
  name: String,
  rank: String = "Cadet"
)