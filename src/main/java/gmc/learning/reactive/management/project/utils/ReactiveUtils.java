package gmc.learning.reactive.management.project.utils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactiveUtils<T> {

	public static <T> Mono<T> toMono(T typeToConvert) {
		Supplier<T> dataGiven = () -> typeToConvert;
		CompletableFuture<T> futureSupplier = CompletableFuture.supplyAsync(dataGiven);
		return Mono.fromFuture(futureSupplier);
	}
	
	public static <T> Flux<T> toFlux(Stream<T> typeToConvert) {
		return Flux.fromStream(typeToConvert);
	}
	
	public static <T> T monoTo(Mono<T> typeToConvert) {
		return typeToConvert.block();
	}
	
	public static <T> List<T> fluxTo(Flux<T> typeToConvert) {
		return typeToConvert.collectList().block();
	}
	
}
