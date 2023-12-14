package aoqia.campfires_enhanced.access;

public interface CampfireBlockEntityAccess {
    long getElapsedTicks();
    void setElapsedTicks(long ticks);
    void increaseElapsedTicks();
    void decreaseElapsedTicks();
    void resetElapsedTicks();
}
