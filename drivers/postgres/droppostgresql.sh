#!/bin/sh -e 


/Library/PostgreSQL/9.0/bin/psql  -h localhost -U nakamura nak  << EOF
DROP TABLE IF EXISTS css CASCADE;
DROP TABLE IF EXISTS ac_css CASCADE;
DROP TABLE IF EXISTS au_css CASCADE;
DROP TABLE IF EXISTS cn_css CASCADE;
DROP TABLE IF EXISTS lk_css CASCADE;
DROP TABLE IF EXISTS css_b CASCADE;
DROP TABLE IF EXISTS au_css_b CASCADE;
DROP TABLE IF EXISTS ac_css_b CASCADE;
DROP TABLE IF EXISTS cn_css_b CASCADE;
DROP TABLE IF EXISTS lk_css_b CASCADE;
DROP TABLE IF EXISTS css_w CASCADE;
DROP TABLE IF EXISTS ac_css_w CASCADE;
DROP TABLE IF EXISTS au_css_w CASCADE;
DROP TABLE IF EXISTS cn_css_w CASCADE;
DROP TABLE IF EXISTS lk_css_w CASCADE;
DROP TABLE IF EXISTS css_wr CASCADE;
EOF




