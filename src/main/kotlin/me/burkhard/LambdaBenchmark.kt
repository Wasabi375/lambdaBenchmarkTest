package me.burkhard

import org.openjdk.jmh.annotations.*
import java.util.*

@State(Scope.Benchmark)
@Fork(value = 1, warmups = 1)
@BenchmarkMode(Mode.AverageTime)
open class LambdaBenchmark {

    private lateinit var data: LongArray

    @Setup(Level.Trial)
    fun prepare() {
        val random = Random()
        data = LongArray(size)
        for (i in 0 until size) {
            data[i] = random.nextLong()
        }
    }

    @Benchmark
    fun sequenceLambda(): Any {
        return data.asSequenceMapLambda(size)
    }

    @Benchmark
    fun sequenceFunRef(): Any {
        return data.asSequenceMapFunRef(size)
    }

    @Benchmark
    fun streamLambda(): Any {
        return data.asStreamMapLambda()
    }

    @Benchmark
    fun streamFunRef(): Any {
        return data.asStreamMapFunRef()
    }

    @Benchmark
    fun parallelStreamLambda(): Any {
        return data.asParallelStreamMapLambda()
    }

    @Benchmark
    fun parallelStreamFunRef(): Any {
        return data.asParallelStreamMapFunRef()
    }

    companion object {
        const val size = 1 shl 27
    }
}

fun Sequence<Long>.toLongArray(size: Int): LongArray {
    val iterator = iterator()
    return LongArray(size) { iterator.next() }
}


fun LongArray.asSequenceMapLambda(size: Int): LongArray = asSequence().map { it.inv() }.toLongArray(size)
fun LongArray.asSequenceMapFunRef(size: Int): LongArray = asSequence().map(Long::inv).toLongArray(size)

fun LongArray.asStreamMapLambda(): LongArray = Arrays.stream(this).map { it.inv() }.toArray()
fun LongArray.asStreamMapFunRef(): LongArray = Arrays.stream(this).map(Long::inv).toArray()

fun LongArray.asParallelStreamMapLambda(): LongArray = Arrays.stream(this).parallel().map { it.inv() }.toArray()
fun LongArray.asParallelStreamMapFunRef(): LongArray = Arrays.stream(this).parallel().map(Long::inv).toArray()