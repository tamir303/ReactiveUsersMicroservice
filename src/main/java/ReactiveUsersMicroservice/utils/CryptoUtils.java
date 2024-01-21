package ReactiveUsersMicroservice.utils;

import org.mindrot.jbcrypt.BCrypt;
import reactor.core.publisher.Mono;

public class CryptoUtils {

    public static Mono<String> hashPassword(String password) {
        return Mono.just(BCrypt.hashpw(password, BCrypt.gensalt()));
    }

    public static Mono<Boolean> checkPassword(String password, String hash) {
        return Mono.just(BCrypt.checkpw(password, hash));
    }
}
