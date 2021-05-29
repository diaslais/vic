package com.laisd.elevator

class Person {
    fun enter(elevator: Elevator): Boolean {
        if (elevator.getNumberOfPeople() < 5){
            elevator.people.add(this)
            return true
        }
        return false
    }

    fun leave(elevator: Elevator): Boolean {
        if (elevator.getNumberOfPeople() > 0) {
            elevator.people.removeAt(elevator.people.size - 1)
            return true
        }
        return false
    }
}