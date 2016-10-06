package com.example.hristijan.tabs2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hristijan.tabs2.database.DatabaseHandler;
import com.example.hristijan.tabs2.dummy.HomeContent;
import com.example.hristijan.tabs2.items.Classroom;
import com.example.hristijan.tabs2.items.Consultation;
import com.example.hristijan.tabs2.items.Lecture;
import com.example.hristijan.tabs2.items.News;
import com.example.hristijan.tabs2.items.Staff;
import com.example.hristijan.tabs2.items.StaffCategory;
import com.example.hristijan.tabs2.items.Study;
import com.example.hristijan.tabs2.items.Subject;
import com.example.hristijan.tabs2.items.SubjectType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IntroFragmenOne.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IntroFragmenOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntroFragmenOne extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btn;
    private TextView title;
    private TextView subtitle;
    private ImageView image;
    int index = -1;
    private SharedPreferences settings;
    private SharedPreferences lectures;
    private SharedPreferences.Editor editor;
    private static DatabaseHandler db;
    private Button welcomeBtn;

    private OnFragmentInteractionListener mListener;

    public IntroFragmenOne() {
        // Required empty public constructor
    }

    public IntroFragmenOne(int index) {
        // Required empty public constructor
        this.index = index;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IntroFragmenOne.
     */
    // TODO: Rename and change types and number of parameters
    public static IntroFragmenOne newInstance(String param1, String param2) {
        IntroFragmenOne fragment = new IntroFragmenOne();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_intro_one, container, false);
        title = (TextView)view.findViewById(R.id.textView3);
        subtitle = (TextView)view.findViewById(R.id.textView4);
        image = (ImageView)view.findViewById(R.id.intro_image_one);

        if(index == 0){
            image.setImageResource(R.drawable.newspaper);
            title.setText("Биди во тек со сите новости и случувања");
            subtitle.setText("Биди секогаш во тек со најновите известувања, настани, вработувања и пракси!");
        } else if(index == 1){
            image.setImageResource(R.drawable.calendar);
            title.setText("Распоред, консултации и испити");
            subtitle.setText("Селектирај ги твоите предмети и добиј го твојот распоред на предавања, консултации и испити. Веќе не мора да се грижиш за промените, твојата апликација ќе се погрижи за тоа!");
        }
        else if(index == 2){
            image.setImageResource(R.drawable.pencil);
            title.setText("Запиши белешка!");
            //
            subtitle.setText("Факултетските белешки, би требало да се наоѓаа во факултетска апликација, нели? :)");
        }

        btn = (Button)view.findViewById(R.id.buttonGo);
        if(index == 2){
            btn.setVisibility(View.VISIBLE);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncFetchNews().execute();

            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        settings = getActivity().getSharedPreferences("welcome", Context.MODE_PRIVATE);
        lectures = getActivity().getSharedPreferences("lectures", Context.MODE_PRIVATE);
        db = new DatabaseHandler(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void fillDatabase(){

        fillStaff(getContext(), R.raw.stafftsv);
        fillClassroom(getContext(), R.raw.rooms);
        //fillCourses(getContext(), R.raw.courses);
        //fillStudies(getContext(), R.raw.studies);
        //fillConsultations(getContext(), R.raw.consultations);

        // Staff Category
        db.addStaffCategory(new StaffCategory("1", "ПРОФЕСОРИ"));
        db.addStaffCategory(new StaffCategory("2", "ДОЦЕНТИ"));
        db.addStaffCategory(new StaffCategory("3", "АСИСТЕНТИ"));
        db.addStaffCategory(new StaffCategory("4", "СОРБОТНИЦИ"));
        db.addStaffCategory(new StaffCategory("5", "ДЕМОНСТРАТОРИ"));


        //fillStaff(getContext(), R.raw.staff);

        // Staff
//        db.addStaff(new Staff("danco.davcev@finki.ukim.mk", "1", "D", "Данчо", "Давчев", "ddavcev", "Роден е 1949 година во Скопје. Дипломирал на Електротехничкиот факултет во Белград (1972). Tитулата доктор-инженер ja стекнал на Универзитетот во Орсеј, Paris – Sud, Франција (1975). Докторирал на Електротехничкиот факултет во Белград (1981). Бил на постдокторски истражувања на универзитетите во Калифорнија (1984-1985). На Електротехничкиот факултет во Скопје бил вработен во 1973 како асистент. Понатаму напредувал во звањата доцент (1980), вонреден професор (1985) и редовен професор (1991). Држел/држи настава по предмети од областа на структури и бази на податоци, дистрибуирани компјутерски системи, мултимедиски системи и бази на податоци, информациони системи, oбјектно-ориентирани системи. Како автор/коавтор има објавено повеќе од 250 труда во меѓународни списанија или зборници од меѓународни конференции, еден универзитетски учебник и книгата “Мултимедиски системи”. Повеќе негови трудови се цитирани (стотина пати) од други автори, што е регистрирано во SCI. Бил раководител на 12 проекти финансирани од Владата на Р. Македонија, 19 меѓународни проекти (финансирани од Националната фондација за наука на САД, ЕУ PHARE, TEMPUS, UNESCO, Владите на Франција, Италија) и повеќе од 16 апликативни проекти за индустријата (финансирани од Искра Делта Љубљана, ЕСМ, Министерства за здравство, економија, одбрана, ветерина, УКИМ и Светска банка). Добитник е на награда “Гоце Делчев” за 1996 година, за исклучителни остварувања во науката. Член е на IEEE (senior member) и на ACM (senior member). Во текот на 2001/02 бил визитинг професор на повеќе универзитети во САД, пред сé во Аризона, Темпи. Полиња на интерес: мултимедиски информациони системи, безжични сензорски мрежи, биотехнологии, кориснички интерфејси, бази на податоци, дистрибуирани системи, пребарување на податоци, екологија, мобилно сметање."));
//        db.addStaff(new Staff("dragan.mihajlov@finki.ukim.mk", "1","D", "Драган", "Михајлов", "dmihajlov", "Роден е 1949 во Скопје. Дипломирал и магистрирал на Електротехничкиот факултет во Загреб во 1973 и 1975 година, а докторирал на Електротехничкиот факултет во Љубљана 1979 година. Во периодот од 1973 до 1983 година работел во Математичкиот институт со нумерички центар при универзитетот „Св. Кирил и Методиј“, каде во 1982 година е избран за доцент. На Електротехничкиот факултеот во Скопје е вработен во 1983 како доцент. Во звањата вонреден и редовен професор бил биран во 1987 и 1992 година, соодветно. Во текот на својата работа имал повеќемесечни студиски престои на Електротехничкиот факултет во Загреб во 1974 година, на Електротехничкиот факултет во Љубљана во 1978 и на Одделот за биоматематика на Универзитетот на Калифорнија во Лос Анџелес во учебната 1984-1985 година. Држел настава по разни предмети од областите на интерес на додипломски и магистерски студии на повеќе факултети (Природно-математички факултет, Воена академија, Медицински факултет, Факултет за физичка култура, Факултет за драмски уметности, Институт за социолошки и политичко правни истражувања). Автор и коавтор е на повеќе од 150 трудови објавени на меѓународни конференции и списанија. Раководел и учествувал во повеќе научно-истражувачки и стручни проекти. Награден е за најдобар труд по биокибернетика на Југословенската конференција за ЕТАН. Области на неговиот интерес се: програмирање, апликативен софтвер, компјутерска графика, мултимедија и биокибернетика."));
//        db.addStaff(new Staff("katerina.zdravskova@finki.ukim.mk", "D", "1", "Катерина", "Здравкова", "dmihajlov", ""));
//        db.addStaff(new Staff("suzana.loskovska@finki.ukim.mk", "1","D", "Сузана", "Лошковска", "dmihajlov", ""));
//        db.addStaff(new Staff("zaneta.popeska@finki.ukim.mk", "1","D", "Жанета", "Попеска", "dmihajlov", ""));
//        db.addStaff(new Staff("verica.bakeva@finki.ukim.mk", "1","D", "Верица", "Бакева", "gjorgji_madzarov", ""));
//        db.addStaff(new Staff("vladimir.trajkovikj@finki.ukim.mk","D", "1", "Владимир", "Трајковиќ", "dmihajlov", ""));
//        db.addStaff(new Staff("dimitar.trajanov@finki.ukim.mk", "1","D", "Димитар", "Трајанов", "gjorgji_madzarov", ""));
//        db.addStaff(new Staff("ana.madevska.bogdanova@finki.ukim.mk","D", "1", "Ана", "Мадеевска Богданова", "dmihajlov", ""));
//        db.addStaff(new Staff("dejan.gjorgjevikj@finki.ukim.mk", "1","D", "Дејан", "Ѓорѓевиќ", "dmihajlov", ""));
//        db.addStaff(new Staff("ljupco.kocarev@finki.ukim.mk", "1","D", "Љупчо", "Коцарев", "dmihajlov", ""));
//        db.addStaff(new Staff("marjan.gusev@finki.ukim.mk", "1","D", "Марјан Гушев", "Михајлов", "dmihajlov", ""));
//        db.addStaff(new Staff("kosta.mitrevski@finki.ukim.mk", "1","D", "Коста", "Митревски", "dmihajlov", ""));
//        db.addStaff(new Staff("andrea.kulakov@finki.ukim.mk", "1","D", "Андреа", "Кулаков", "gjorgji_madzarov", ""));
//        db.addStaff(new Staff("ljupco.antovski@finki.ukim.mk", "1","D", "Љупчо", "Антовски", "gjorgji_madzarov", ""));
//        db.addStaff(new Staff("marija.mihova@finki.ukim.mk", "1","D", "Марија", "Михова", "gjorgji_madzarov", ""));
//        db.addStaff(new Staff("slobodan.kalajdziski@finki.ukim.mk","D", "1", "Слободан", "Калајџиски", "dmihajlov", ""));
//        db.addStaff(new Staff("nevena.ackovska@finki.ukim.mk", "1","D", "Невена", "Ацковска", "dmihajlov", ""));
//        db.addStaff(new Staff("goran.velinov@finki.ukim.mk", "1","D", "Горан", "Велинов", "dmihajlov", ""));
//        db.addStaff(new Staff("atanas.misev@finki.ukim.mk", "1","D", "Атанас", "Мишев", "dmihajlov", ""));
//        db.addStaff(new Staff("sonja.filiposka@finki.ukim.mk", "1","D", "Соња", "Филипоска", "dmihajlov", ""));
//        db.addStaff(new Staff("ivan.corbev@finki.ukim.mk", "1", "D","Иван", "Чорбев", "dmihajlov", ""));
//        db.addStaff(new Staff("lasko.basnarov@finki.ukim.mk", "1", "D","Ласко", "Баснаров", "dmihajlov", ""));
//        db.addStaff(new Staff("boro.jakimovski@finki.ukim.mk", "1", "D","Боро", "Јакимовски", "dmihajlov", ""));
//        db.addStaff(new Staff("vesna.dimitrova@finki.ukim.mk", "1", "D","Весна", "Димитрова", "dmihajlov", ""));
//        db.addStaff(new Staff("goce.ermenski@finki.ukim.mk", "1", "D","Гоце", "Ерменски", "gjorgji_madzarov", ""));

//        db.addStaff(new Staff("dejan.spasov@finki.ukim.mk", "2", "Дејан", "Спасов", "dmihajlov", ""));
//        db.addStaff(new Staff("ivica.dimitrovski@finki.ukim.mk", "2", "Ивица", "Димитровски", "dmihajlov", ""));
//        db.addStaff(new Staff("igor.miskovski@finki.ukim.mk", "2", "Игор", "Мишковски", "dmihajlov", ""));
//        db.addStaff(new Staff("gjorgji.madzarov@finki.ukim.mk", "2", "Ѓорѓи", "Маџаров", "gjorgji_madzarov", ""));
//        db.addStaff(new Staff("sasko.ristov@finki.ukim.mk", "2", "Сашко", "Ристов", "dmihajlov", ""));
//        db.addStaff(new Staff("vangel.ajanovski@finki.ukim.mk", "2", "Вангел", "Ајановски", "dmihajlov", ""));
//        db.addStaff(new Staff("mile.jovanov@finki.ukim.mk", "2", "Миле", "Јованов", "dmihajlov", ""));
//        db.addStaff(new Staff("kire.trivodaliev@finki.ukim.mk", "2", "Кире", "Триводалиев", "dmihajlov", ""));
//        db.addStaff(new Staff("saso.gramatikov@finki.ukim.mk", "2", "Сашо", "Граматиков", "dmihajlov", ""));
//
//
//
//        db.addStaff(new Staff("tomche.delev@finki.ukim.mk", "3", "Томче", "Делев", "gjorgji_madzarov", ""));
//        db.addStaff(new Staff("pance.ribarski@finki.ukim.mk", "3", "Панче", "Рибарски", "dmihajlov", ""));
//        db.addStaff(new Staff("milosh.jovanovski@finki.ukim.mk", "3", "Милош", "Јовановски", "gjorgji_madzarov", ""));
//        db.addStaff(new Staff("riste.stojanov@finki.ukim.mk", "3", "Ристе", "Стојанов", "dmihajlov", ""));
//        db.addStaff(new Staff("petre.lameski@finki.ukim.mk", "3", "Петре", "Ламески", "dmihajlov", ""));
//        db.addStaff(new Staff("emil.stankov@finki.ukim.mk", "3", "Емил", "Станков", "dmihajlov", ""));
//        db.addStaff(new Staff("hristina.mihajlovska@finki.ukim.mk", "3", "Христина", "Михајловска", "dmihajlov", ""));
//
//
//        db.addStaff(new Staff("igor.kulev@finki.ukim.mk", "4", "Игор", "Кулев", "dmihajlov", ""));
//        db.addStaff(new Staff("kristina.spirovska@finki.ukim.mk", "4", "Кристина", "Спировска", "dmihajlov", ""));
//        db.addStaff(new Staff("igor.trajkovski@finki.ukim.mk", "4", "Игор", "Трајковски", "dmihajlov", ""));
//
//        db.addStaff(new Staff("aleksandar.tenev@finki.ukim.mk", "5", "Александар", "Тенев", "dmihajlov", ""));
//        db.addStaff(new Staff("aleksandra.bogojevska@finki.ukim.mk", "5", "Александра", "Богојевска", "dmihajlov", ""));
//        db.addStaff(new Staff("vesna.kirandziska@finki.ukim.mk", "5", "Весна", "Киранџиска", "dmihajlov", ""));
//        db.addStaff(new Staff("bojan.ilijoski@finki.ukim.mk", "5", "Бојан", "Илијоски", "dmihajlov", ""));



        // Classroom
//        db.addClassroom(new Classroom("117", "117(Ф)", "на приземје, ходник десно од главниот влез, последна врата од лево (просторијата каде се вршеше запишувањето на ФИНКИ)"));
//        db.addClassroom(new Classroom("114", "114(Ф)", "на приземје, ходник десно од главниот влез, простории од десна страна"));
//        db.addClassroom(new Classroom("115", "115(Ф)", "на приземје, ходник десно од главниот влез, простории од десна страна"));
//        db.addClassroom(new Classroom("116", "116(Ф)", "на приземје, ходник десно од главниот влез, простории од десна страна"));
//        db.addClassroom(new Classroom("201", "201(Ф)", "први спрат, десно од скалите, па повторно десно"));
//        db.addClassroom(new Classroom("203", "203(Ф)", "први спрат, десно од скалите, па повторно десно"));
//        db.addClassroom(new Classroom("2XX", "2XX", "први спрат, лево од скалите, па повторно лево"));
//        db.addClassroom(new Classroom("315", "315(Ф)", "втори спрат, лево од скалите, па повторно лево"));
//        db.addClassroom(new Classroom("301", "301(Ф)", "втори кат, десно од скалите, па повторно десно"));
//        db.addClassroom(new Classroom("302", "302(Ф)", "втори кат, десно од скалите, па повторно десно"));
//        db.addClassroom(new Classroom("Барака 1", "Барака 1", "Барака 1"));
//        db.addClassroom(new Classroom("Барака 2.1", "Барака 2.1", "Барака 2.1"));
//        db.addClassroom(new Classroom("Барака 2.2", "Барака 2.2", "Барака 2.2"));
//        db.addClassroom(new Classroom("Барака 3.1", "Барака 3.1", "Барака 3.1"));
//        db.addClassroom(new Classroom("Барака 3.2", "Барака 3.2", "Барака 3.2"));
//        db.addClassroom(new Classroom("123(Ф)", "123(Ф)", "прв спрат, десно од скалите"));
//        db.addClassroom(new Classroom("112(Ф)", "112(Ф)", "прв спрат, десно од скалите"));
//        db.addClassroom(new Classroom("223(М)", "223(М)", "втор спрат, лево од скалите"));
//        db.addClassroom(new Classroom("225(М)", "225(М)", "втор спрат, десно од скалите"));
//        db.addClassroom(new Classroom("Л200а", "Л200а", "на прв кат, училница што пишува ФИЗИКА - 40 работни места"));
//        db.addClassroom(new Classroom("Л200в", "Л200в", "на прв кат, училница што пишува ФИЗИКА - 40 работни места"));
//        db.addClassroom(new Classroom("Лабараторија PolarCape", "Лабараторија PolarCape", "на прв кат, до скалите"));
//        db.addClassroom(new Classroom("Л215", "Л215", "на прв кат, лево од скалите, втората училница - 40 работни места"));
//        db.addClassroom(new Classroom("Л3", "Л3", "под Деканат на ТМФ, по скалите кои водат до бифето на ТМФ на десниот крај на ходникот - 20 работни места"));
//        db.addClassroom(new Classroom("Л26", "Л26", "десно од бифето на ТМФ, до крајот на левата страна на ходникот - 20 работни места"));
//
//        db.addClassroom(new Classroom("Просторија 35", "Просторија 35", "десно од бифето на ТМФ, лева страна од ходникот, на десна страна"));
//        db.addClassroom(new Classroom("Анекс кабинети", "Анекс кабинети", "Анекс кабинети..."));
//        db.addClassroom(new Classroom("Институт КНИ", "Институт КНИ", "Институт КНИ..."));


        // Subject Type
        db.addSubjectType(new SubjectType("P", "Предавање"));
        db.addSubjectType(new SubjectType("A", "Аудиториски вежби"));
        db.addSubjectType(new SubjectType("L", "Лабараториски вежби"));

        // Subjects
        db.addSubject(new Subject("11", "Структурно програмирање", 1, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("12", "Вовед во информатика", 1, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("13", "Дискретна математика 1", 1, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("14", "Професионални вештини", 1, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("15", "Калкулус 1", 1, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("16", "Логички кола и дискретни автомати", 1, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("17", "Математика 1", 1, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("18", "Вовед во интернет", 1, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("19", "Физика", 1, false, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("110", "Основи на електрични кола", 1, true, "Описот не е достапен...", 6, "2+2+1"));


        db.addSubject(new Subject("21", "Објектно-ориентирано програмирање", 2, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("22", "Архитектура и организација на компјутери", 2, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("23", "Дискретна математика 2", 2, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("24", "Калкулус 2", 2, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("25", "Основи на Веб Дизајн", 2, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("26", "Компјутерски компоненти (и периферии)", 2, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("27", "Бизнис и менаџмент", 2, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("28", "Математика 2", 2, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("29", "Модерна физика", 2, false, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("30", "Електротехника и компјутерски науки", 2, true, "Описот не е достапен...", 6, "2+2+1"));


        db.addSubject(new Subject("31", "Алгоритми и податочни структури", 3, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("32", "Компјутерски мрежи", 3, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("33", "Веројатност и статистика", 3, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("34", "Маркетинг", 3, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("35", "Интерактивни апликации ", 3, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("36", "Интернет програмирање", 3, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("37", "Логичко и функционално програмирање ", 3, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("38", "Објектно ориентирани системи", 3, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("39", "ИТ Системи за учење", 3, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("310", "Напредно програмирање", 3, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("311", "Мултимедиски технологии", 3, true, "Описот не е достапен...", 6, "2+2+1"));


        db.addSubject(new Subject("41", "Визуелно програмирање", 4, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("42", "Дигитално процесирање на сигнали", 4, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("43", "Софтверско инженерство", 4, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("44", "Основи на компјутерска графика", 4, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("45", "Интернет технологии", 4, false, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("46", "Теорија на информации со дигитални комуникации", 4, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("47", "Вештачка интелигенција", 4, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("48", "Медиуми и комуникации", 4, false, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("49", "Оперативни системи", 4, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("401", "Алгоритми и сложеност", 4, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("409", "Безжични и мобилни системи", 4, true, "Описот не е достапен...", 6, "2+2+1"));

        db.addSubject(new Subject("51", "Системи на знаење", 5, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("52", "Веб програмирање", 5, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("53", "Вовед во роботика", 5, false, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("54", "Криптографија", 5, false, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("55", "Мобилни платформи и програмирање", 5, false, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("56", "Менаџмент информациски системи", 5, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("57", "Бази на податоци", 5, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("58", "Микропроцесорски системи", 5, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("59", "Визуелизација", 5, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("510", "Мултимедиски системи", 5, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("511", "Дизајн и архитектура на софтвер", 5, true, "Описот не е достапен...", 6, "2+2+1"));

        db.addSubject(new Subject("61", "Компајлери", 6, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("62", "Безбедност на компјутерски системи", 6, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("63", "Дигитално процесирање на сигнали", 6, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("64", "Сензорски системи", 6, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("65", "Напредни бази на податоци", 6, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("66", "Податочно рударство", 6, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("67", "Роботика", 6, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("68", "Паралелно програмирање", 6, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("69", "Мобилни апликации", 6, false, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("60", "Електронска и мобилна трговија", 6, false, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("666", "Интелигентни системи", 6, true, "Описот не е достапен...", 6, "2+2+1"));

        db.addSubject(new Subject("71", "Методологија на истражувањето во ИКТ", 7, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("72", "Компјутерска етика", 7, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("73", "Веб базирани системи", 7, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("74", "Паралелно и дистрибуирано процесирање", 7, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("75", "Обработка на природните јазици", 7, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("76", "Напреден веб дизајн", 7, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("77", "Иновации во ИКТ", 7, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("78", "Анализа и дизајн на ИС", 7, false, "Описот не е достапен...", 6, "2+2+1"));


        db.addSubject(new Subject("81", "Софтверски квалитет и тестирање", 8, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("82", "Географски информациони системи", 8, false, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("83", "Претприемништво", 8, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("84", "Управување со ИКТ проекти", 8, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("85", "Неструктурирани бази на податоци", 8, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("86", "Виртуелна реалност", 8, false, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("87", "Сервисно Ориентирани Архитектури", 8, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("88", "Управување со мрежи", 8, true, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("89", "Компјутерска анимација", 8, false, "Описот не е достапен...", 6, "2+2+1"));
        db.addSubject(new Subject("80", "Дипломска работа", 8, true, "Описот не е достапен...", 6, "2+2+1"));


        // Lectures
//        db.addLecture(new Lecture("1", "24", "P", "verica.bakeva@finki.ukim.mk", "117", "08:00", "09:45", 1));
//        db.addLecture(new Lecture("2", "23", "P", "marija.mihova@finki.ukim.mk", "117", "11:00", "13:45", 1));
//        db.addLecture(new Lecture("3", "45", "P", "goce.ermenski@finki.ukim.mk", "223(М)", "08:00", "09:45", 4));
//        db.addLecture(new Lecture("4", "49", "А", "milosh.jovanovski@finki.ukim.mk", "Барака 2.2", "18:00", "19:45", 3));
//        db.addLecture(new Lecture("5", "84", "P", "dimitar.trajanov@finki.ukim.mk", "Барака 3.2", "12:00", "13:45", 2));
//        db.addLecture(new Lecture("6", "84", "P", "ljupco.antovski@finki.ukim.mk", "Барака 3.2", "10:00", "11:45", 2));
//        db.addLecture(new Lecture("7", "24", "P", "verica.bakeva@finki.ukim.mk", "117", "16:00", "17:45", 3));
//        db.addLecture(new Lecture("8", "47", "P", "andrea.kulakov@finki.ukim.mk", "117", "08:00", "09:45", 5));
//        db.addLecture(new Lecture("9", "41", "A", "tomche.delev@finki.ukim.mk", "117", "13:00", "13:45", 3));
//        db.addLecture(new Lecture("10", "55", "A", "riste.stojanov@finki.ukim.mk", "117", "13:00", "13:45", 3));


        // Consultations
//
//        db.addConsultation(new Consultation("ljupcho.antovski@finki.ukim.mk", "35", "09:30", "10:00", 2));
//        db.addConsultation(new Consultation("verica.bakeva@finki.ukim.mk", "L26", "11:00", "13:00", 2));
//        db.addConsultation(new Consultation("verica.bakeva@finki.ukim.mk", "L26", "12:00", "13:00", 3));
//        db.addConsultation(new Consultation("tomche.delev@finki.ukim.mk", "l26", "10:30", "12:00", 3));
//
//        db.addConsultation(new Consultation("andrea.kulakov@finki.ukim.mk", "35", "10:00", "12:00", 4));
//        db.addConsultation(new Consultation("dimitar.trajanov@finki.ukim.mk", "35", "19:30", "21:00", 5));
//        db.addConsultation(new Consultation("marija.mihova@finki.ukim.mk", "35", "09:00", "10:30", 5));

        //fillConsultations(getContext(), R.raw.cons);

        db.addConsultation(new Consultation("1", "marija.mihova@finki.ukim.mk", "35", "09:00", "10:00", 1));
        db.addConsultation(new Consultation("2", "verica.bakeva@finki.ukim.mk", "L26", "11:00", "13:00", 2));
        db.addConsultation(new Consultation("3", "ljupco.antovski@finki.ukim.mk", "35", "09:30", "10:00", 2));
        db.addConsultation(new Consultation("4", "dimitar.trajanov@finki.ukim.mk", "35", "11:00", "13:00", 3));
        db.addConsultation(new Consultation("5", "dimitar.trajanov@finki.ukim.mk", "35", "19:00", "21:00", 4));
        db.addConsultation(new Consultation("6", "andrea.kulakov@finki.ukim.mk", "35", "15:00", "17:00", 3));
        db.addConsultation(new Consultation("7", "andrea.kulakov@finki.ukim.mk", "35", "10:00", "12:00", 4));
        db.addConsultation(new Consultation("8", "goce.ermenski@finki.ukim.mk", "L3", "10:00", "12:00", 1));
        db.addConsultation(new Consultation("9", "goce.ermenski@finki.ukim.mk", "L3", "16:00", "18:00", 3));
        db.addConsultation(new Consultation("10", "tomche.delev@finki.ukim.mk", "L3", "10:00", "12:00", 5));

        //db.addNews(new News("1", "Проверка", "Проверка...", "15:06 25-08-2016"));


        //        ITEMS.add(new Study("KNI", "Компјутерски науки и инженерство", "", 1));
//        ITEMS.add(new Study("KNI", "Мрежни технологии", "", 1));
//        ITEMS.add(new Study("KNI", "Примена на е-технологии", "", 1));
//        ITEMS.add(new Study("KNI", "Информатика и компјутерско инженерство", "", 1));
//        ITEMS.add(new Study("KNI", "Кодирање и криптографија", "", 2));
//        ITEMS.add(new Study("KNI", "Управување во информатички технологии", "", 2));
//        ITEMS.add(new Study("KNI", "Компјутерски науки и инженерство", "", 3));
//        ITEMS.add(new Study("KNI", "Компјутерски науки и инженерство", "", 3));

//        db.addStudy(new Study("Компјутерски науки и инженерство", "", 1));
//        db.addStudy(new Study("Мрежни технологии", "", 1));
//        db.addStudy(new Study("Примена на е-технологии", "", 1));
//        db.addStudy(new Study("Кодирање и криптографија", "", 2));


    }

    public static void fillCourses(Context context, int resourceID) {
        InputStream inputStream = context.getResources().openRawResource(resourceID);
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputReader);

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                String ID = data[0];
                String NAME = data[1];
                int SEMESTER = Integer.parseInt(data[2]);
                boolean MANDATORY = Boolean.parseBoolean(data[3]);
                String DESCRIPTION = data[4];
                int ECTS = Integer.parseInt(data[5]);

                try{
                    String CURRICULUM = data[6];
                    db.addSubject(new Subject(ID, NAME, SEMESTER, MANDATORY, DESCRIPTION, ECTS, CURRICULUM));
                } catch (Exception eee){
                    db.addSubject(new Subject(ID, NAME, SEMESTER, MANDATORY, DESCRIPTION, ECTS, "Нема достапни информации во моментов..."));
                }


            }
        } catch (IOException e) {
            //something
        }
    }

    public static void fillClassroom(Context context, int resourceID) {
        InputStream inputStream = context.getResources().openRawResource(resourceID);
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputReader);

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                String ID     = data[0];
                String NAME   = data[1];
                String DESCRIPTION   = data[2];

                db.addClassroom(new Classroom(ID, NAME, DESCRIPTION));
            }
        } catch (IOException e) {
            //something
        }
    }

    public static void fillStudies(Context context, int resourceID) {
        InputStream inputStream = context.getResources().openRawResource(resourceID);
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputReader);

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                String NAME     = data[0];
                String DESCRIPTION   = data[1];
                int LEVEL = Integer.parseInt(data[2]);

                db.addStudy(new Study(NAME, DESCRIPTION, LEVEL));
            }
        } catch (IOException e) {
            //something
        }
    }

    public static void fillConsultations(Context context, int resourceID) {
        InputStream inputStream = context.getResources().openRawResource(resourceID);
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputReader);

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                String ID     = data[0];
                String EMAIL     = data[1];
                String LAB  = data[2];
                String FROM = data[3];
                String TO = data[5];
                int DAY_IN_WEEK  = Integer.parseInt(data[5]);
//
//                db.addConsultation(new Consultation(ID, EMAIL, LAB, FROM, TO, DAY_IN_WEEK));
            }
        } catch (IOException e) {
            //something
        }
    }

    public static void fillStaff(Context context, int resourceID) {
        InputStream inputStream = context.getResources().openRawResource(resourceID);
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputReader);

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\t");
                Log.i("DATAAA", data.toString());
                String EMAIL     = data[0];
                String CATEGORY   = data[1];
                String LEVEL   = data[2];
                String FIRST_NAME   = data[3];
                String LAST_NAME   = data[4];
                String IMAGE_URL   = data[5];
                String RESUME   = data[6];

                db.addStaff(new Staff(EMAIL, CATEGORY, LEVEL, FIRST_NAME, LAST_NAME, IMAGE_URL, RESUME));
            }
        } catch (IOException e) {
            //something
        }
    }

//    private boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }

    private class AsyncFetchSchedule extends AsyncTask<String, String, String> {
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;

        //ProgressDialog pdLoading;
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pdLoading = ProgressDialog.show(SplashScreen.this, "", "", true);
        }

        @Override
        protected String doInBackground(String... params) {

                try {
                    fillDatabase();
                    // РАСПОРЕД
                    url = new URL("http://pastebin.com/raw/0ZV4P9ZV");
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return e.toString();
                }
                try {
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(READ_TIMEOUT);
                    conn.setConnectTimeout(CONNECTION_TIMEOUT);
                    conn.setRequestMethod("GET");

                    conn.setDoOutput(true);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    return e1.toString();
                }

                try {

                    int response_code = conn.getResponseCode();

                    if (response_code == HttpURLConnection.HTTP_OK) {

                        InputStream input = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        StringBuilder result = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }

                        editor = lectures.edit();
                        editor.putInt("version", 1);
                        editor.commit();

                        return (result.toString());
                    } else {
                        return ("unsuccessful");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return e.toString();
                } finally {
                    conn.disconnect();
                }
            }


        @Override
        protected void onPostExecute(final String result) {

                try {
                    JSONArray jArray = new JSONArray(result);

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);

                        db.addLecture(new Lecture(json_data.getString("id"), json_data.getString("subject_id"), json_data.getString("subject_type"), json_data.getString("staff_id"), json_data.getString("classroom_id"), json_data.getString("from"), json_data.getString("to"), json_data.getInt("day")));
                    }
                    Log.i("rann", "done");

                } catch (JSONException e) {
                    Log.i("GRESKA", e.toString());
                    Log.i("GRESKA", e.getMessage());
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                } finally {

//                    new AsyncFetchScheduleVersion().execute();
//
//                    Log.i("fajnli", "yes");
//                    Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
//                    startActivity(intent);
//                    finish();
                }

        }
    }


    private class AsyncFetchNews extends AsyncTask<String, String, String> {
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;

        ProgressDialog pdLoading;
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading = ProgressDialog.show(getContext(), "Конфигурација", "Почекајте...", true);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                fillDatabase();

                url = new URL("http://pastebin.com/raw/VdVwwHGY");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString();
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                conn.setDoOutput(true);

            } catch (IOException e1) {
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                if (response_code == HttpURLConnection.HTTP_OK) {

                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    return (result.toString());

                } else {
                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                editor = settings.edit();
                editor.putBoolean("intro", false);
                editor.commit();
                conn.disconnect();
            }

        }

        @Override
        protected void onPostExecute(String result) {

            pdLoading.dismiss();
            new AsyncFetchSchedule().execute();

            try {

                JSONArray jArray = new JSONArray(result);

                HomeContent.ITEMS.clear();
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    HomeContent.ITEMS.add(new News(json_data.getString("id"), json_data.getString("title"), json_data.getString("content"), json_data.getString("type"), json_data.getString("image_url")));
                }

                Intent intent = new Intent(getContext(), NavigationDrawerActivity.class);
                startActivity(intent);
                getActivity().finish();

            } catch (JSONException e) {
                Log.i("GRRESKA", e.toString());
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            }

        }

    }


}
