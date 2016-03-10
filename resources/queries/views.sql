use crawler;

CREATE VIEW num_docs_with_term AS
SELECT term_id, count(distinct document_id) as totalDocs
FROM terms_documents 
GROUP BY term_id;

CREATE VIEW num_terms_in_doc AS
SELECT document_id, sum(termCount) as totalTerms
FROM crawler.terms_documents 
GROUP BY document_id;

CREATE VIEW num_times_term_appears AS
SELECT term_id, document_id, termCount
FROM terms_documents
ORDER BY term_id;

CREATE VIEW tf AS
SELECT A.term_id, A.document_id, (1 + LOG(10, A.termCount)) AS tf
FROM num_times_term_appears A, num_terms_in_doc B
WHERE A.document_id = B.document_id;

CREATE VIEW idf AS
SELECT B.term_id, LOG(10, (COUNT(A.id) / B.totalDocs)) AS idf
FROM documents A, num_docs_with_term B
GROUP BY B.term_id;
