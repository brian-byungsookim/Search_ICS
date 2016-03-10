SELECT id FROM terms WHERE term = "2009";

SELECT B.url, B.id, A.TFIDF
FROM (SELECT A.term_id, A.document_id, A.tf * B.idf AS TFIDF
		FROM tf A, idf B
		WHERE A.term_id = B.term_id AND A.term_id = 4 AND B.term_id = 4) AS A, documents B
WHERE B.id = A.document_id
ORDER BY TFIDF DESC
