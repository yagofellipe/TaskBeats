package com.comunidadedevspace.taskbeats

interface MyCountRepository {

    fun sum(p1: Int, p2: Int): Int
}

class MyCountRepositoryIMpl: MyCountRepository{
    override fun sum(p1: Int, p2: Int): Int {
        return p1 + p2
    }


}