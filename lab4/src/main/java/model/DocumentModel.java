package model;

import util.GeometryUtil;
import util.Point;

import java.util.*;

public class DocumentModel {

    public final static double SELECTION_PROXIMITY = 10;

    // Kolekcija svih grafičkih objekata:
    private List<GraphicalObject> objects = new ArrayList<>();

    // Read-Only proxy oko kolekcije grafičkih objekata:
    private List<GraphicalObject> roObjects = Collections.unmodifiableList(objects);

    // Kolekcija prijavljenih promatrača:
    private List<DocumentModelListener> listeners = new ArrayList<>();

    // Kolekcija selektiranih objekata:
    private List<GraphicalObject> selectedObjects = new ArrayList<>();

    // Read-Only proxy oko kolekcije selektiranih objekata:
    private List<GraphicalObject> roSelectedObjects = Collections.unmodifiableList(selectedObjects);

    // Promatrač koji će biti registriran nad svim objektima crteža...
    private final GraphicalObjectListener goListener = new GraphicalObjectListener() {

        @Override
        public void graphicalObjectChanged(GraphicalObject go) {
            notifyListeners();
        }

        @Override
        public void graphicalObjectSelectionChanged(GraphicalObject go) {
            if(go.isSelected() && !selectedObjects.contains(go)) {
                selectedObjects.add(go);
            }
            else if(!go.isSelected() && selectedObjects.contains(go)){
                selectedObjects.remove(go);
            }
            roSelectedObjects = Collections.unmodifiableList(selectedObjects);
            notifyListeners();
        }
    };

    // Konstruktor...
    public DocumentModel() {

    }

    // Brisanje svih objekata iz modela (pazite da se sve potrebno odregistrira)
    // i potom obavijeste svi promatrači modela
    public void clear() {
        for( GraphicalObject object : objects){
            object.removeGraphicalObjectListener(goListener);
        }
        objects.clear();
        selectedObjects.clear();
        notifyListeners();
    }

    // Dodavanje objekta u dokument (pazite je li već selektiran; registrirajte model kao promatrača)
    public void addGraphicalObject(GraphicalObject obj) {
        obj.addGraphicalObjectListener(goListener);
        objects.add(obj);
        if(obj.isSelected()) {
            selectedObjects.add(obj);
            roSelectedObjects = Collections.unmodifiableList(selectedObjects);
        }
        roObjects = Collections.unmodifiableList(objects);
        notifyListeners();
    }

    // Uklanjanje objekta iz dokumenta (pazite je li već selektiran; odregistrirajte model kao promatrača)
    public void removeGraphicalObject(GraphicalObject obj) {
        obj.removeGraphicalObjectListener(goListener);
        objects.remove(obj);
        if(obj.isSelected()) {
            selectedObjects.remove(obj);
            roSelectedObjects = Collections.unmodifiableList(selectedObjects);
        }
        roObjects = Collections.unmodifiableList(objects);
        notifyListeners();
    }

    // Vrati nepromjenjivu listu postojećih objekata (izmjene smiju ići samo kroz metode modela)
    public List<GraphicalObject> list() {
        return roObjects;
    }

    public void addDocumentModelListener(DocumentModelListener l) {
        listeners.add(l);
    }

    public void removeDocumentModelListener(DocumentModelListener l) {
        listeners.remove(l);
    }


    public void notifyListeners() {
        for(var l : listeners){
            l.documentChange();
        }
    }

    // Vrati nepromjenjivu listu selektiranih objekata
    public List<GraphicalObject> getSelectedObjects() {
        return roSelectedObjects;
    }

    public void clearSelection(){
        for(GraphicalObject o : objects)
            o.setSelected(false);
    }

    // Pomakni predani objekt u listi objekata na jedno mjesto kasnije...
    // Time će se on iscrtati kasnije (pa će time možda veći dio biti vidljiv)
    public void increaseZ(GraphicalObject go) {

        int index = objects.indexOf(go);
        if(index == objects.size()-1) return;
        objects.add(index + 2, go);
        objects.remove(index);

        roObjects = Collections.unmodifiableList(objects);
        notifyListeners();
    }

    // Pomakni predani objekt u listi objekata na jedno mjesto ranije...
    public void decreaseZ(GraphicalObject go) {

        int index = objects.indexOf(go);
        if(index == 0) return;
        objects.add(index-1, go);
        objects.remove(index+1);

        roObjects = Collections.unmodifiableList(objects);
        notifyListeners();
    }

    // Pronađi postoji li u modelu neki objekt koji klik na točku koja je
    // predana kao argument selektira i vrati ga ili vrati null. Točka selektira
    // objekt kojemu je najbliža uz uvjet da ta udaljenost nije veća od
    // SELECTION_PROXIMITY. Status selektiranosti objekta ova metoda NE dira.
    public GraphicalObject findSelectedGraphicalObject(Point mousePoint) {
        try {
            GraphicalObject closestObject = objects.stream()
                    .min(Comparator.comparing(o -> o.selectionDistance(mousePoint))).get();

            double distance = closestObject.selectionDistance(mousePoint);
            if(distance > SELECTION_PROXIMITY)
                return null;

            return closestObject;
        }
        catch (NoSuchElementException e){
            return null;
        }
    }

    // Pronađi da li u predanom objektu predana točka miša selektira neki hot-point.
    // Točka miša selektira onaj hot-point objekta kojemu je najbliža uz uvjet da ta
    // udaljenost nije veća od SELECTION_PROXIMITY. Vraća se indeks hot-pointa
    // kojeg bi predana točka selektirala ili -1 ako takve nema. Status selekcije
    // se pri tome NE dira.
    public int findSelectedHotPoint(GraphicalObject object, Point mousePoint) {
        int index = -1;
        double minDistance = Double.POSITIVE_INFINITY;

        for(int i=0; i<object.getNumberOfHotPoints(); i++){
            double distance = GeometryUtil.distanceFromPoint(object.getHotPoint(i), mousePoint);
            if( distance < minDistance && distance < SELECTION_PROXIMITY){
                index = i;
                minDistance = distance;
            }
        }
        return index;
    }




}