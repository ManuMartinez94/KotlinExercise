package com.kotlinexercise.utils

inline val Int.seconds get() = this.toLong() * 1000

inline val Int.minutes get() = this.toLong() * 60000

inline val Int.hours get() = this.toLong() * 3600000

inline val Int.days get() = this.toLong() * 86400000

inline val Int.weeks get() = this.toLong() * 604800000
