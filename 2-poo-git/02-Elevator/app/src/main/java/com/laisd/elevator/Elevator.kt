package com.laisd.elevator

class Elevator(var currentFloor: Int, var people: MutableList<Person>) {

    fun goToFloor(floor: Int) {
        currentFloor = floor
    }

    fun getNumberOfPeople() = people.size
}