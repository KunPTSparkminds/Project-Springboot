package net.sparkminds.utils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import lombok.Data;
import net.sparkminds.entity.Project;
import net.sparkminds.service.dto.response.ApplicationResponseDTO;
import net.sparkminds.service.dto.response.ProjectResponseDTO;

@Data
public class PDFGenerator {

	private List<ApplicationResponseDTO> applicationList;

	public void generate(HttpServletResponse response) throws DocumentException, IOException {

		Document document = new Document(PageSize.A3);

		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();

		Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTiltle.setSize(20);

		// Creating paragraph
		Paragraph paragraph = new Paragraph("List Of Applications", fontTiltle);

		// Aligning the paragraph in document
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		// Adding the created paragraph in document
		document.add(paragraph);

		// Creating a table of 4 columns
		PdfPTable table = new PdfPTable(2);

		// Setting width of table, its columns and spacing
		table.setWidthPercentage(100f);
		table.setWidths(new int[] { 4, 8 });
		table.setSpacingBefore(5);

		// Create Table Cells for table header
		PdfPCell cell = new PdfPCell();

		// Setting the background color and padding
		cell.setBackgroundColor(CMYKColor.BLUE);
		cell.setPadding(5);

		// Creating font
		// Setting font style and size
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(CMYKColor.WHITE);

		// Adding headings in the created table cell/ header
		// Adding Cell to table
		cell.setPhrase(new Phrase("Personal Information", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Project", font));
		table.addCell(cell);

		// Iterating over the list of students
		for (ApplicationResponseDTO application : applicationList) {
			System.out.print(application);
			table.addCell(String.format("- Name: %s \n- Github Account: %s \n- Email: %s", application.getGithubUser(),
					application.getName(), application.getEmailAdress()));
			List<String> test1 = application.getProjects().stream().map((test) -> {
				return String.format(
						"- Name:  %s, Role: %s, Duration: %s, Mode: %s, Team Size: %s, Capacity: %s, Start Year: %s, Link To Repo: %s, Link To Live URL: %s ",
						test.getNameProject(), test.getRole(), test.getDuration(), test.getEmploymentMode(),
						test.getTeamSize(), test.getCapacity(), test.getStartYear(), test.getLinkToRepo(),
						test.getLinkToLiveUrl());
			}).collect(Collectors.toList());
			String project = String.join(" \n", test1);
			table.addCell(project);
		}
		// Adding the created table to document
		document.add(table);

		// Closing the document
		document.close();

	}
}