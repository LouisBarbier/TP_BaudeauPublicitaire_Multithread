package bandeau;

public class ExampleScenario {

    /**
     * "Programme principal" : point d'entrée d'exécution
     *
     * @param args les "arguments de ligne de commande", transmis au lancement du programme
     */
    public static void main(String[] args) {
        String message;
        if (args.length > 0) // J'ai au moins un paramètre
        {
            message = args[0]; // le premier paramètre
        } else {
            message = "Démonstration du bandeau";
        }

        ExampleScenario instance = new ExampleScenario();
        instance.exemple(message);
    }

    public void exemple(String message) {
        // On cree un scenario
        Scenario s = new Scenario();
        // On lui ajoute des effets
        //s.addEffect(new FontEnumerator(10), 1);
        s.addEffect(new RandomEffect("Le jeu du pendu", 700), 1);
        s.addEffect(new TeleType("Je m'affiche caractère par caractère", 100), 1);
        s.addEffect(new Blink("Je clignote 10x", 100), 10);
        s.addEffect(new Zoom("Je zoome", 50), 1);
        s.addEffect(new FontEnumerator(10), 1);
        s.addEffect(new Rainbow("Comme c'est joli !", 30), 1);
        s.addEffect(new Rotate("2 tours à droite", 180, 4000, true), 2);
        s.addEffect(new Rotate("2 tours à gauche", 180, 4000, false), 2);
        // On cree le bandeau
        SharedBaudeau b = new SharedBaudeau();
        b.setMessage(message);
        b.sleep(2000);
        // On joue le scenario sur le bandeau
        s.playOn(b);
        b.close();
    }

}
