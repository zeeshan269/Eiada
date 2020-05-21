package com.eiadatech.eiada.Fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.eiadatech.eiada.R;
import com.eiadatech.eiada.Retrofit.Models.ReportModel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.eiadatech.eiada.Adapters.ReportAdapter.selectedReport;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewReportFragment extends Fragment {

    private TextView textViewDate, textViewTime, textViewDoctor, textViewPatient, textViewPdf;
    private ImageView btnPdf;
    private TextView textViewReference, textViewDuration, textViewComplaint, textViewImpression, textViewHistory, textViewPlan, textViewMedication;
    private ConstraintLayout constraintLayout;
    private File pdfFile;

    public ViewReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_report, container, false);
        initializeViews(view);
        return view;
    }


    private void initializeViews(View view) {


        textViewDate = view.findViewById(R.id.textView72);
        textViewTime = view.findViewById(R.id.textView70);
        textViewDoctor = view.findViewById(R.id.textView76);
        textViewPatient = view.findViewById(R.id.textView74);
        btnPdf = view.findViewById(R.id.imageView17);
        textViewPdf = view.findViewById(R.id.textView98);

        textViewDuration = view.findViewById(R.id.textView78);
        textViewReference = view.findViewById(R.id.textView80);
        textViewComplaint = view.findViewById(R.id.editText8);
        textViewImpression = view.findViewById(R.id.editText9);
        textViewHistory = view.findViewById(R.id.editText10);
        textViewPlan = view.findViewById(R.id.editText11);
        textViewMedication = view.findViewById(R.id.editText12);

        constraintLayout = view.findViewById(R.id.constraintLayout);


        textViewDoctor.setText(selectedReport.getProfessionalName());
        textViewPatient.setText(selectedReport.getPatientName());
        textViewTime.setText(selectedReport.getTime());
        textViewDate.setText(selectedReport.getDate());
        textViewDuration.setText(selectedReport.getDuration());
        textViewReference.setText(selectedReport.getReference());
        textViewComplaint.setText(selectedReport.getComplaint());
        textViewImpression.setText(selectedReport.getImpression());
        textViewHistory.setText(selectedReport.getAssesment());
        textViewPlan.setText(selectedReport.getPlan());
        textViewMedication.setText(selectedReport.getMedication());


        btnPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    generatePdf(selectedReport);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });


        textViewPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    generatePdf(selectedReport);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void generatePdf(ReportModel reportModel) throws IOException, DocumentException {

        //check runtime permissions for android

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                //File write logic here
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1122);
            }
        }

        //creating a Folder
        File docsFolder = new File(Environment.getExternalStorageDirectory(), "EIADA");
        if (!docsFolder.exists()) {
            docsFolder.mkdirs();
            try {
                docsFolder.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("NEW", "Created a new directory for PDF");
        }


        pdfFile = new File(docsFolder, "Report-" + reportModel.getDate() + ".pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, output);
        document.open();
        // Document Settings
        document.setPageSize(PageSize.A4);
        document.addCreationDate();
        document.addAuthor("Eiada Health Care");
        document.addCreator("Syed Tanveer");

        Font font = FontFactory.getFont("res/font/roboto_bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font2 = FontFactory.getFont("res/font/roboto_regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Paragraph header = new Paragraph();
//        Image image = getPdfImageStream();
//        // Image image2 = Image.getInstance(new URL(imageUrl));
//        image.scaleToFit(110f, 110f);
//        Chunk chunk = new Chunk(image, 0, -85);
//        header.add(chunk);

        // glue for moving the text of paragraph to right

//


        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setLockedWidth(true);

        PdfPCell pdfCell = new PdfPCell(new Phrase("Consultation Report", new Font(font.getFamily(), 11f, Font.BOLD, new BaseColor(255, 255, 255))));
        pdfCell.setBackgroundColor(new BaseColor(73, 135, 128));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setPaddingBottom(10f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        pdfCell = new PdfPCell(new Phrase("EIADA Health Care", new Font(font.getFamily(), 11f, Font.BOLD, new BaseColor(255, 255, 255))));
        pdfCell.setBackgroundColor(new BaseColor(73, 135, 128));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setPaddingBottom(10f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(pdfCell);

        document.add(table);

        table = new PdfPTable(1);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setLockedWidth(true);
        pdfCell = new PdfPCell(new Phrase("Consultation Details", new Font(font.getFamily(), 9f, Font.BOLD, new BaseColor(0, 100, 0))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setPaddingBottom(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        document.add(table);

        table = new PdfPTable(4);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setWidths(new int[]{1, 2, 1, 2, });
        table.setLockedWidth(true);

        pdfCell = new PdfPCell(new Phrase("Date", new Font(font.getFamily(), 8f, Font.BOLD, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(5f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        pdfCell = new PdfPCell(new Phrase(reportModel.getDate(), new Font(font.getFamily(), 8f, Font.BOLD|Font.UNDERLINE, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(5f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        pdfCell = new PdfPCell(new Phrase("Duration", new Font(font.getFamily(), 8f, Font.BOLD, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(5f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        pdfCell = new PdfPCell(new Phrase(reportModel.getDuration(), new Font(font.getFamily(), 8f, Font.BOLD|Font.UNDERLINE, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(5f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);


        document.add(table);

        table = new PdfPTable(4);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setWidths(new int[]{1, 2, 1, 2, });
        table.setLockedWidth(true);

        pdfCell = new PdfPCell(new Phrase("Time", new Font(font.getFamily(), 8f, Font.BOLD, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        pdfCell = new PdfPCell(new Phrase(reportModel.getTime(), new Font(font.getFamily(), 8f, Font.BOLD|Font.UNDERLINE, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        pdfCell = new PdfPCell(new Phrase("Reference No", new Font(font.getFamily(), 8f, Font.BOLD, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        pdfCell = new PdfPCell(new Phrase(reportModel.getReference(), new Font(font.getFamily(), 8f, Font.BOLD|Font.UNDERLINE, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        document.add(table);

        table = new PdfPTable(1);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setLockedWidth(true);
        pdfCell = new PdfPCell(new Phrase("Patient Details", new Font(font.getFamily(), 9f, Font.BOLD, new BaseColor(0, 100, 0))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);
        document.add(table);

        table = new PdfPTable(4);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setWidths(new int[]{1, 2, 1, 2, });
        table.setLockedWidth(true);

        pdfCell = new PdfPCell(new Phrase("Patient Name", new Font(font.getFamily(), 8f, Font.BOLD, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        pdfCell = new PdfPCell(new Phrase(reportModel.getPatientName(), new Font(font.getFamily(), 8f, Font.BOLD|Font.UNDERLINE, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        pdfCell = new PdfPCell(new Phrase("Date of Birth", new Font(font.getFamily(), 8f, Font.BOLD, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        pdfCell = new PdfPCell(new Phrase(reportModel.getDateOfBirth(), new Font(font.getFamily(), 8f, Font.BOLD|Font.UNDERLINE, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        document.add(table);

        table = new PdfPTable(4);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setWidths(new int[]{1, 2, 1, 2, });
        table.setLockedWidth(true);

        pdfCell = new PdfPCell(new Phrase("Gender", new Font(font.getFamily(), 8f, Font.BOLD, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        pdfCell = new PdfPCell(new Phrase(reportModel.getGender(), new Font(font.getFamily(), 8f, Font.BOLD|Font.UNDERLINE, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        pdfCell = new PdfPCell(new Phrase("Email", new Font(font.getFamily(), 8f, Font.BOLD, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        pdfCell = new PdfPCell(new Phrase(reportModel.getEmail(), new Font(font.getFamily(), 8f, Font.BOLD|Font.UNDERLINE, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        document.add(table);


        table = new PdfPTable(1);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setLockedWidth(true);
        pdfCell = new PdfPCell(new Phrase("Doctor Details", new Font(font.getFamily(), 9f, Font.BOLD, new BaseColor(0, 100, 0))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);
        document.add(table);



        table = new PdfPTable(4);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setWidths(new int[]{1, 2, 1, 2, });
        table.setLockedWidth(true);

        pdfCell = new PdfPCell(new Phrase("Doctor Name", new Font(font.getFamily(), 8f, Font.BOLD, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        pdfCell = new PdfPCell(new Phrase(reportModel.getProfessionalName(), new Font(font.getFamily(), 8f, Font.BOLD|Font.UNDERLINE, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        pdfCell = new PdfPCell(new Phrase("License No", new Font(font.getFamily(), 8f, Font.BOLD, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        pdfCell = new PdfPCell(new Phrase(reportModel.getLicense(), new Font(font.getFamily(), 8f, Font.BOLD|Font.UNDERLINE, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);

        document.add(table);



        table = new PdfPTable(1);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setLockedWidth(true);
        pdfCell = new PdfPCell(new Phrase("Diagnosis", new Font(font.getFamily(), 9f, Font.BOLD, new BaseColor(0, 100, 0))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(15f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);
        document.add(table);

        table = new PdfPTable(1);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setLockedWidth(true);
        pdfCell = new PdfPCell(new Phrase(reportModel.getDiagnosis(), new Font(font.getFamily(), 8f, Font.BOLD, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);
        document.add(table);


        table = new PdfPTable(1);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setLockedWidth(true);
        pdfCell = new PdfPCell(new Phrase("Chief Complaint", new Font(font.getFamily(), 9f, Font.BOLD, new BaseColor(0, 100, 0))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(15f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);
        document.add(table);

        table = new PdfPTable(1);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setLockedWidth(true);
        pdfCell = new PdfPCell(new Phrase(reportModel.getComplaint(), new Font(font.getFamily(), 8f, Font.BOLD, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);
        document.add(table);


        table = new PdfPTable(1);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setLockedWidth(true);
        pdfCell = new PdfPCell(new Phrase("History and Assessment", new Font(font.getFamily(), 9f, Font.BOLD, new BaseColor(0, 100, 0))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(15f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);
        document.add(table);

        table = new PdfPTable(1);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setLockedWidth(true);
        pdfCell = new PdfPCell(new Phrase(reportModel.getAssesment(), new Font(font.getFamily(), 8f, Font.BOLD, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);
        document.add(table);


        table = new PdfPTable(1);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setLockedWidth(true);
        pdfCell = new PdfPCell(new Phrase("Impression", new Font(font.getFamily(), 9f, Font.BOLD, new BaseColor(0, 100, 0))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);
        document.add(table);

        table = new PdfPTable(1);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setLockedWidth(true);
        pdfCell = new PdfPCell(new Phrase(reportModel.getImpression(), new Font(font.getFamily(), 8f, Font.BOLD, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);
        document.add(table);


        document.newPage();
        table = new PdfPTable(1);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setLockedWidth(true);
        pdfCell = new PdfPCell(new Phrase("Plan", new Font(font.getFamily(), 9f, Font.BOLD, new BaseColor(0, 100, 0))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);
        document.add(table);

        table = new PdfPTable(1);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setLockedWidth(true);
        pdfCell = new PdfPCell(new Phrase(reportModel.getPlan(), new Font(font.getFamily(), 8f, Font.BOLD, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);
        document.add(table);


        table = new PdfPTable(1);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setLockedWidth(true);
        pdfCell = new PdfPCell(new Phrase("Recommended Medication", new Font(font.getFamily(), 9f, Font.BOLD, new BaseColor(0, 100, 0))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(20f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);
        document.add(table);

        table = new PdfPTable(1);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setLockedWidth(true);
        pdfCell = new PdfPCell(new Phrase(reportModel.getMedication(), new Font(font.getFamily(), 8f, Font.BOLD, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(10f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(pdfCell);
        document.add(table);

        table = new PdfPTable(1);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setLockedWidth(true);
        pdfCell = new PdfPCell(new Phrase(reportModel.getProfessionalName(), new Font(font.getFamily(), 8f, Font.BOLD, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingTop(20f);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(pdfCell);
        document.add(table);



        Calendar calander = Calendar.getInstance();
        DateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");

        String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};

        Calendar cal = Calendar.getInstance();
        String month = monthName[cal.get(Calendar.MONTH)];

        final Date c = Calendar.getInstance().getTime();

        SimpleDateFormat dayf = new SimpleDateFormat("dd");
        SimpleDateFormat yearf = new SimpleDateFormat("YYYY");
        String day = dayf.format(c);
        String year = yearf.format(c);

        String date = month + ", " + day + " " + year;
        String time = simpleDateFormat.format(calander.getTime());

        table = new PdfPTable(1);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setLockedWidth(true);
        pdfCell = new PdfPCell(new Phrase("Report generated at " + time + ", " + date, new Font(font.getFamily(), 8f, Font.BOLD, new BaseColor(105, 105, 105))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(pdfCell);
        document.add(table);


        table = new PdfPTable(1);
        table.setTotalWidth(PageSize.A4.getWidth() * 0.86f);
        table.setLockedWidth(true);
        pdfCell = new PdfPCell(new Phrase("www.admin.eiada.com", new Font(font.getFamily(), 8f, Font.BOLD, new BaseColor(0, 100, 0))));
        pdfCell.setBorder(PdfPCell.NO_BORDER);
        pdfCell.setPaddingRight(5f);
        pdfCell.setPaddingLeft(5f);
        pdfCell.setPaddingBottom(10f);
        pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(pdfCell);
        document.add(table);


        document.add(new Paragraph("\n"));
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(192, 192, 192, 68));
        document.add(lineSeparator);

        document.close();
        previewPdf();

    }

    private void previewPdf() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                //File write logic here
                PackageManager packageManager = getActivity().getPackageManager();
                Intent testIntent = new Intent(Intent.ACTION_VIEW);
                testIntent.setType("application/pdf");
                List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
                if (list.size() > 0) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    Uri uri = FileProvider.getUriForFile(getActivity(), "eiadahealth.provider", pdfFile);
                    intent.setDataAndType(uri, "application/pdf");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
                }
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1123);
            }
        }


    }


}
