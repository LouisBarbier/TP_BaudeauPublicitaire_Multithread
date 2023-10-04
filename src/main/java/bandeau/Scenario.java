package bandeau;
import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Classe utilitaire pour représenter la classe-association UML
 */
class ScenarioElement {

    Effect effect;
    int repeats;

    ScenarioElement(Effect e, int r) {
        effect = e;
        repeats = r;
    }
}
/**
 * Un scenario mémorise une liste d'effets, et le nombre de repetitions pour chaque effet
 * Un scenario sait se jouer sur un bandeau.
 */
public class Scenario {

    private final Lock verrou = new ReentrantLock();
    private boolean beingPlayed = false;
    private final Condition played = verrou.newCondition();
    private boolean beingChanged = false;
    private final Condition changed = verrou.newCondition();
    private final List<ScenarioElement> myElements = new LinkedList<>();

    /**
     * Ajouter un effect au scenario.
     *
     * @param e l'effet à ajouter
     * @param repeats le nombre de répétitions pour cet effet
     */
    public boolean addEffect(Effect e, int repeats){
        verrou.lock();
        try {
            while (beingPlayed) {
                played.await();
            }
            if (beingPlayed) {
                return false;
            }
            beingChanged=true;
            myElements.add(new ScenarioElement(e, repeats));
            beingChanged=false;
            changed.signalAll();
            return true;
        } catch (InterruptedException ex) {
            return false;
        } finally {
            verrou.unlock();
        }
    }

    /**
     * Jouer ce scenario sur un bandeau
     *
     * @param b le bandeau ou s'afficher.
     */
    public void playOn(SharedBaudeau b){
        Thread t = new Thread(
                () -> {
                    b.verrouille();
                    for (ScenarioElement element : myElements) {
                        for (int repeats = 0; repeats < element.repeats; repeats++) {
                            element.effect.playOn(b);
                        }
                    }
                    b.deverrouille();
                }
        );

        t.start();
    }
}
