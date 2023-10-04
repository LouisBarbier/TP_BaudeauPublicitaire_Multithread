package bandeau;

public class ExerciceAvecThreads {

    public static void main(String[] args) {
        ExerciceAvecThreads instance = new ExerciceAvecThreads();
        instance.exemple();
    }

    public void exemple() {

        Scenario s = makeScenario();
        // On cree les bandeaux
        SharedBaudeau b1 = new SharedBaudeau();
        SharedBaudeau b2 = new SharedBaudeau();
        SharedBaudeau b3 = new SharedBaudeau();

        System.out.println("CTRL-C pour terminer le programme");
        // On doit jouer le scénario en même temps sur les trois bandeaux
        s.playOn(b1);
        s.playOn(b2);
        s.playOn(b3);
        // On rejoue le scénario sur b1 quand le premier jeu est fini
        s.playOn(b1);
    }

    private Scenario makeScenario() {
        // On crée un scenario
        Scenario s = new Scenario();
        // On lui ajoute des effets
        while(!s.addEffect(new RandomEffect("Le jeu du pendu", 700), 1)){}
        // s.addEffect(new TeleType("Je m'affiche caractère par caractère", 100), 1);
        // s.addEffect(new Blink("Je clignote 10x", 100), 10);
        // s.addEffect(new Zoom("Je zoome", 50), 1);
        // s.addEffect(new FontEnumerator(10), 1);
        // s.addEffect(new Rainbow("Comme c'est joli !", 30), 1);
        while(!s.addEffect(new Rotate("2 tours à droite", 180, 4000, true), 2)){}
        // s.addEffect(new Rotate("2 tours à gauche", 180, 4000, false), 2);
        return s;
    }
}
