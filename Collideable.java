public interface Collideable<T>{
    boolean didCollidePlatform(T other);
    boolean didCollideDog(T other);
}