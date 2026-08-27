import os
import shutil
from reportlab.lib.pagesizes import letter
from reportlab.lib import colors
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, Table, TableStyle, KeepTogether, HRFlowable

downloads_dir = os.path.join(os.path.expanduser('~'), 'Downloads')
pdf_path = os.path.join(downloads_dir, 'CodeClash_Presentation_Script.pdf')
local_pdf_path = os.path.join(os.path.dirname(__file__), 'CodeClash_Presentation_Script.pdf')

doc = SimpleDocTemplate(
    pdf_path,
    pagesize=letter,
    leftMargin=36,
    rightMargin=36,
    topMargin=36,
    bottomMargin=36
)

styles = getSampleStyleSheet()

title_style = ParagraphStyle(
    'DocTitle',
    parent=styles['Heading1'],
    fontName='Helvetica-Bold',
    fontSize=18,
    leading=22,
    textColor=colors.HexColor('#0f172a'),
    alignment=1,
    spaceAfter=3
)

subtitle_style = ParagraphStyle(
    'DocSubtitle',
    parent=styles['Normal'],
    fontName='Helvetica',
    fontSize=10,
    leading=13,
    textColor=colors.HexColor('#475569'),
    alignment=1,
    spaceAfter=12
)

slide_header_style = ParagraphStyle(
    'SlideHeader',
    parent=styles['Heading2'],
    fontName='Helvetica-Bold',
    fontSize=11.5,
    leading=14,
    textColor=colors.HexColor('#1e40af')
)

slide_time_style = ParagraphStyle(
    'SlideTime',
    parent=styles['Normal'],
    fontName='Helvetica-Bold',
    fontSize=9,
    leading=11,
    textColor=colors.HexColor('#059669'),
    alignment=2
)

meta_style = ParagraphStyle(
    'MetaText',
    parent=styles['Normal'],
    fontName='Helvetica-Oblique',
    fontSize=8.5,
    leading=11,
    textColor=colors.HexColor('#64748b')
)

speech_style = ParagraphStyle(
    'SpeechText',
    parent=styles['Normal'],
    fontName='Helvetica',
    fontSize=9,
    leading=13,
    textColor=colors.HexColor('#0f172a')
)

qa_q_style = ParagraphStyle(
    'QAQuestion',
    parent=styles['Normal'],
    fontName='Helvetica-Bold',
    fontSize=9,
    leading=12,
    textColor=colors.HexColor('#991b1b')
)

qa_a_style = ParagraphStyle(
    'QAAnswer',
    parent=styles['Normal'],
    fontName='Helvetica',
    fontSize=8.8,
    leading=12.5,
    textColor=colors.HexColor('#1e293b')
)

elements = []

elements.append(Paragraph('CodeClash — Presentation Speaking Script', title_style))
elements.append(Paragraph('FSJP Mini Project &bull; Computer Engineering (2026-27) &bull; K. C. College of Engineering, Thane', subtitle_style))
elements.append(HRFlowable(width='100%', thickness=1.5, color=colors.HexColor('#2563eb'), spaceAfter=10))

slides_data = [
    (
        'Slide 1: Title Slide',
        '~20 sec',
        'Title, College details, Group: Prince Dubey, Krish Jadhav, Abhishek Yadav, Shivam Yadav, Guide: Prof. Shweta Gaikwad',
        '"Good morning respected teachers and fellow students. Today, our team—Prince, Abhishek, Shivam, and myself, Krish—under the guidance of Prof. Shweta Gaikwad, is excited to present our FSJP project: <b>CodeClash — A Real-Time 1v1 Competitive Coding Arena</b>."'
    ),
    (
        'Slide 2: Outline of Presentation',
        '~15 sec',
        'Presentation roadmap from Introduction to System Architecture & Methodology',
        '"Here is the brief roadmap of today\'s presentation. We will walk you through the core problem we identified, the architecture of our platform, our full-stack implementation using Spring Boot and Java, and how CodeClash bridges the gap in modern coding practice."'
    ),
    (
        'Slide 3: Introduction',
        '~30 sec',
        'CodeClash platform overview + 6 core highlights (Spring Boot, Cloud, Judging, Elo, Auth, MySQL)',
        '"Most of us practice Data Structures & Algorithms on platforms like LeetCode, but the experience is largely solitary. <b>CodeClash</b> transforms DSA practice into a real-time head-to-head battle. Two players join a private room, receive the exact same problem with a countdown timer, write Java solutions, and are evaluated instantly by a sandboxed judge engine. The winner is determined by correctness and speed, and their official Elo rating updates dynamically after each match."'
    ),
    (
        'Slide 4: Problem Statement',
        '~30 sec',
        'No real-time 1v1 practice, remote partners cannot practice together, lack of peer skill tracking',
        '"When analyzing current platforms, we observed three major challenges:<br/>1. Platforms like LeetCode and Codeforces focus either on solo grinding or scheduled global contests—there is no lightweight way to do an instant 1v1 battle with a friend.<br/>2. Study partners are not always on the same local network, which is why a 24/7 cloud-accessible platform is needed.<br/>3. Without direct peer-to-peer competition and rating ladders, students often lose motivation during daily practice."'
    ),
    (
        'Slide 5: Literature Survey',
        '~30 sec',
        'Comparison table: LeetCode, Codeforces, HackerRank vs CodeClash',
        '"In our literature survey, we compared leading platforms: <i>LeetCode</i> has a vast problem set, but lacks spontaneous live 1v1 dueling. <i>Codeforces</i> hosts rated rounds, but they require strict scheduling. <i>HackerRank</i> is tailored for recruitment tests rather than casual peer rivalry. <b>CodeClash fills this gap</b> by providing instant room-based matchmaking, real-time opponent telemetry, automated test grading, and a Chess-style Elo ranking system."'
    ),
    (
        'Slide 6: Aim and Objectives',
        '~25 sec',
        'Core aim + Primary & Secondary objectives',
        '"Our primary aim was to engineer a robust, cloud-hosted 1v1 battle platform. Key objectives achieved include: 6-character private room matchmaking; a sub-second sandboxed compiler and judge engine with watchdog protection against infinite loops; standard K=32 Elo ranking calculations and a global leaderboard; and secure BCrypt authentication with zero local installation required."'
    ),
    (
        'Slide 7: System Architecture',
        '~35 sec',
        'Data Flow: Browser -> Spring Boot REST -> Code Execution Engine -> MySQL (Render Cloud)',
        '"Here is our System Architecture: Both players connect from their browsers to our <b>Spring Boot REST backend</b> deployed on Render. When a duel begins, the backend distributes the same problem and synchronized timer. As code is submitted, our <b>Code Execution Engine</b> executes the solution in an isolated sandbox against hidden test cases. Results, test verdicts, and newly calculated Elo ratings are immediately persisted to the <b>MySQL database</b> and synced back to the frontend."'
    ),
    (
        'Slide 8: Methodology & Tech Stack',
        '~30 sec',
        'Full-Stack Java: Spring Boot 3.3.4, Java 21/25, JPA/Hibernate, BCrypt, HTML5/CSS, Docker',
        '"For our technology stack: <b>Backend:</b> Built on Spring Boot 3.3 and Java 21/25 with Spring Data JPA and Hibernate for robust ORM mapping. <b>Security:</b> BCrypt hashing for credential protection. <b>Frontend:</b> Modern responsive HTML5, custom CSS design system with Monaco Editor integration. <b>Execution & Deployment:</b> Multi-stage Docker containerization deployed on Render with an embedded/MySQL database pipeline."'
    ),
    (
        'Slide 9: References',
        '~10 sec',
        'Spring Boot docs, Judge0 CE, MySQL reference, Elo Rating methodology, GitHub repository',
        '"These are the technical references and platform documentations that guided our design, including Spring Boot, Judge0, and the standard Elo rating framework."'
    ),
    (
        'Slide 10: Conclusion & Q/A',
        '~15 sec',
        'Thank You slide + Opening floor for teacher questions',
        '"In conclusion, CodeClash brings gamification and friendly rivalry into daily programming practice. Thank you for your time, and we are now open to any questions."'
    )
]

for title, duration, visual, speech in slides_data:
    header_table = Table(
        [[Paragraph(title, slide_header_style), Paragraph(duration, slide_time_style)]],
        colWidths=[410, 130]
    )
    header_table.setStyle(TableStyle([
        ('VALIGN', (0,0), (-1,-1), 'MIDDLE'),
        ('BOTTOMPADDING', (0,0), (-1,-1), 0),
        ('TOPPADDING', (0,0), (-1,-1), 0),
        ('LEFTPADDING', (0,0), (-1,-1), 0),
        ('RIGHTPADDING', (0,0), (-1,-1), 0),
    ]))
    
    body_content = [
        header_table,
        Spacer(1, 3),
        Paragraph(f'<b>Context:</b> {visual}', meta_style),
        Spacer(1, 3),
        Paragraph(f'<b>Script:</b> {speech}', speech_style)
    ]
    
    card_table = Table([[body_content]], colWidths=[540])
    card_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,-1), colors.HexColor('#f8fafc')),
        ('BOX', (0,0), (-1,-1), 1, colors.HexColor('#cbd5e1')),
        ('TOPPADDING', (0,0), (-1,-1), 6),
        ('BOTTOMPADDING', (0,0), (-1,-1), 6),
        ('LEFTPADDING', (0,0), (-1,-1), 8),
        ('RIGHTPADDING', (0,0), (-1,-1), 8),
    ]))
    
    elements.append(KeepTogether([card_table, Spacer(1, 6)]))

elements.append(Spacer(1, 6))
elements.append(Paragraph('💡 Teacher Q&A Cheat Sheet (Prepared Answers)', slide_header_style))
elements.append(HRFlowable(width='100%', thickness=1, color=colors.HexColor('#cbd5e1'), spaceAfter=6))

qa_list = [
    ('Q1: How does your code execution / judging engine ensure security and prevent infinite loops?',
     '<b>Answer:</b> We compile and run Java code in isolated subprocesses with restricted heap memory (<code>-Xmx128m</code>) and enforce a strict 3000ms watchdog timer per test case to abort infinite loops or blocking calls before grading.'),
    ('Q2: How is the Elo Rating calculated for players?',
     '<b>Answer:</b> We implement the standard Chess K=32 Elo algorithm. If a lower-rated player defeats a higher-rated favorite, they earn a larger rating bonus (+24 to +30), ensuring a fair and competitive leaderboard.'),
    ('Q3: Is the application live and accessible from anywhere?',
     '<b>Answer:</b> Yes, it is fully containerized with Docker and hosted 24/7 on Render at <code>https://codeclash-p5dd.onrender.com</code>, so anyone with a browser on desktop or mobile can duel instantly.')
]

for q, a in qa_list:
    qa_content = [
        Paragraph(q, qa_q_style),
        Spacer(1, 2),
        Paragraph(a, qa_a_style)
    ]
    qa_card = Table([[qa_content]], colWidths=[540])
    qa_card.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,-1), colors.HexColor('#fef2f2')),
        ('BOX', (0,0), (-1,-1), 1, colors.HexColor('#fecaca')),
        ('TOPPADDING', (0,0), (-1,-1), 5),
        ('BOTTOMPADDING', (0,0), (-1,-1), 5),
        ('LEFTPADDING', (0,0), (-1,-1), 8),
        ('RIGHTPADDING', (0,0), (-1,-1), 8),
    ]))
    elements.append(KeepTogether([qa_card, Spacer(1, 5)]))

doc.build(elements)
shutil.copyfile(pdf_path, local_pdf_path)
print(f'SUCCESS: PDF generated at: {pdf_path}')
