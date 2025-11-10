/*
__________        _____                           ___________            .__
\______   \ _____/ ____\____  ______ ____  ____   \_   _____/ ___________|__| ____  ____
 |       _// __ \   __\/  _ \/  ___// ___\/  _ \   |    __)_ /    \_  __ \  |/ ___\/  _ \
 |    |   \  ___/|  | (  <_> )___ \\  \__(  <_> )  |        \   |  \  | \/  \  \__(  <_> )
 |____|_  /\___  >__|  \____/____  >\___  >____/  /_______  /___|  /__|  |__|\___  >____/
        \/     \/                \/     \/                \/     \/              \/
Classe 5BII
TPSIT
*/

public class Main {
    public static void main(String[] args){
        API api = new API();
        System.out.println(api.fetchArtisti());
        System.out.println(api.fetchArtista(1));
    }
}